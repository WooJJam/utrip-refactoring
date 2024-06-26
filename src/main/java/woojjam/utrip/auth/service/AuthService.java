package woojjam.utrip.auth.service;

import woojjam.utrip.auth.dto.LoginResponse;
import woojjam.utrip.auth.dto.TokenDto;
import woojjam.utrip.auth.dto.request.ChangePasswordRequest;
import woojjam.utrip.auth.dto.request.LocalLoginRequest;
import woojjam.utrip.auth.dto.request.RegisterRequest;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.exception.UserException;
import woojjam.utrip.common.JwtUtils;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.user.domain.User;
import woojjam.utrip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        String nickname = registerRequest.getNickname();
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new UserException(StatusCode.DUPLICATE_EMAIL);
        }
        User user = new User();
        user.createUser(nickname,email,password,null);
        userRepository.save(user);
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> checkEmailDuplicate(String email) {
        if (!userRepository.existsByEmail(email)) {
            return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
        }
        throw new UserException(StatusCode.DUPLICATE_EMAIL);
    }

    public ResponseEntity<?> localLogin(LocalLoginRequest localLoginRequest) {
        Optional<User> findUser = userRepository.findByEmailAndPassword(localLoginRequest.getEmail(), localLoginRequest.getPassword());
        if (findUser.isPresent()) {
            String accessToken = jwtUtils.generateToken(findUser.get().getEmail(), 1000 * 60 * 60, "AccessToken");
            String refreshToken = jwtUtils.generateToken(findUser.get().getEmail(), 1000 * 60 * 60 * 24, "RefreshToken");
            findUser.get().updateRefreshToken(refreshToken);
            userRepository.save(findUser.get());
            TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
            LoginResponse response = LoginResponse.of(findUser.get(), tokenDto);
            return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), response));
        } else {
            throw new UserException(StatusCode.USER_NOT_FOUND);
        }
    }

    public ResponseEntity<?> reissue(String token) {
        if (jwtUtils.isValidToken(token)) {
            Optional<User> findUser = userRepository.findByRefreshToken(token);
            if (findUser.isPresent()) {
                String accessToken = jwtUtils.generateToken(findUser.get().getEmail(), 1000 * 60 * 60, "AccessToken");
                String refreshToken = jwtUtils.generateToken(findUser.get().getEmail(), 1000 * 60 * 60 * 24, "RefreshToken");
                findUser.get().updateRefreshToken(refreshToken);
                TokenDto tokenDto = new TokenDto(accessToken, refreshToken);
                return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), tokenDto));
            } else {
                throw new UserException(StatusCode.USER_NOT_FOUND);
            }
        }
        throw new UserException(StatusCode.LOGIN_REQUIRED);
    }

    @Transactional(readOnly = true)
    public boolean isUserEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = changePasswordRequest.getEmail();
        String newPassword = changePasswordRequest.getNewPassword();
        String checkPassword = changePasswordRequest.getCheckPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));

        if (newPassword.equals(checkPassword)) {
            user.changePassword(newPassword);
        } else {
            throw new UserException(StatusCode.PASSWORD_NOT_MATCH);
        }
    }
}