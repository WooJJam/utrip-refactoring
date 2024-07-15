package woojjam.utrip.domains.auth.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.common.security.jwt.AccessTokenProvider;
import woojjam.utrip.common.security.jwt.RefreshTokenProvider;
import woojjam.utrip.domains.auth.dto.LoginResponse;
import woojjam.utrip.domains.auth.dto.TokenDto;
import woojjam.utrip.domains.auth.dto.request.ChangePasswordRequest;
import woojjam.utrip.domains.auth.dto.request.LocalLoginRequest;
import woojjam.utrip.domains.auth.dto.request.RegisterRequest;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.user.exception.UserErrorCode;
import woojjam.utrip.domains.user.exception.UserException;
import woojjam.utrip.domains.user.repository.UserRepository;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AccessTokenProvider accessTokenProvider;
	private final RefreshTokenProvider refreshTokenProvider;
	private final UserRepository userRepository;

	public ResponseEntity<?> register(RegisterRequest registerRequest) {

		String nickname = registerRequest.getNickname();
		String email = registerRequest.getEmail();
		String password = registerRequest.getPassword();
		String role = registerRequest.getRole();
		Optional<User> findUser = userRepository.findByEmail(email);
		// if (findUser.isPresent()) {
		// 	throw new UserException(StatusCode.DUPLICATE_EMAIL);
		// }
		User user = User.of(nickname, email, password, null, role);
		userRepository.save(user);
		return ResponseEntity.ok(SuccessResponse.noContent());
	}

	@Transactional(readOnly = true)
	public ResponseEntity<?> checkEmailDuplicate(String email) {
		if (Boolean.FALSE.equals(userRepository.existsByEmail(email))) {
			return ResponseEntity.ok(SuccessResponse.noContent());
		}
		throw new UserException(UserErrorCode.EMAIL_ALREADY_EXISTS);
	}

	public ResponseEntity<?> localLogin(LocalLoginRequest localLoginRequest) {
		User user = userRepository.findByEmailAndPassword(localLoginRequest.getEmail(), localLoginRequest.getPassword())
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
		String accessToken = accessTokenProvider.generateToken(user.getEmail());
		String refreshToken = refreshTokenProvider.generateToken(user.getEmail());
		user.updateRefreshToken(refreshToken);
		userRepository.save(user);
		LoginResponse response = LoginResponse.of(user, TokenDto.of(accessToken, refreshToken));

		return ResponseEntity.ok(SuccessResponse.of(response));
	}

	public ResponseEntity<?> reissue(String token) {
		// if (jwtUtils.isValidToken(token)) {
		// 	Optional<User> findUser = userRepository.findByRefreshToken(token);
		// 	if (findUser.isPresent()) {
		// 		String accessToken = jwtUtils.generateToken(findUser.get().getEmail(), 1000 * 60 * 60, "AccessToken");
		// 		String refreshToken = jwtUtils.generateToken(findUser.get().getEmail(), 1000 * 60 * 60 * 24,
		// 			"RefreshToken");
		// 		findUser.get().updateRefreshToken(refreshToken);
		// 		TokenDto tokenDto = TokenDto.of(accessToken, refreshToken);
		// 		return ResponseEntity.ok(
		// 			SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), tokenDto));
		// 	} else {
		// 		throw new UserException(StatusCode.USER_NOT_FOUND);
		// 	}
		// }
		// throw new UserException(StatusCode.);
		throw new RuntimeException("수정해야함");
	}

	@Transactional(readOnly = true)
	public boolean isUserEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		String email = changePasswordRequest.getEmail();
		String newPassword = changePasswordRequest.getNewPassword();
		String checkPassword = changePasswordRequest.getCheckPassword();

		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		if (newPassword.equals(checkPassword)) {
			user.changePassword(newPassword);
		} else {
			System.out.println("수정해야");
			// throw new UserException(StatusCode.PASSWORD_NOT_MATCH);
		}
	}
}