package woojjam.utrip.domains.auth.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.domains.auth.dto.LoginResponse;
import woojjam.utrip.domains.auth.dto.TokenDto;
import woojjam.utrip.domains.auth.dto.request.ChangePasswordRequest;
import woojjam.utrip.domains.auth.dto.request.LocalLoginRequest;
import woojjam.utrip.domains.auth.dto.request.RegisterRequest;
import woojjam.utrip.domains.auth.helper.JwtHelper;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.user.exception.UserErrorCode;
import woojjam.utrip.domains.user.exception.UserException;
import woojjam.utrip.domains.user.repository.UserRepository;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtHelper jwtHelper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<?> register(RegisterRequest registerRequest) {

		String nickname = registerRequest.getNickname();
		String email = registerRequest.getEmail();
		String password = registerRequest.getPassword();
		String encode = passwordEncoder.encode(password);
		Optional<User> findUser = userRepository.findByEmail(registerRequest.getEmail());
		if (findUser.isPresent()) {
			throw new UserException(UserErrorCode.EMAIL_ALREADY_EXISTS);
		}
		User user = RegisterRequest.toEntity(nickname, email, encode);
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

		TokenDto tokenDto = jwtHelper.generateToken(user.getEmail());

		user.updateRefreshToken(tokenDto.getRefreshToken());
		userRepository.save(user);

		LoginResponse response = LoginResponse.of(user, tokenDto);
		return ResponseEntity.ok(SuccessResponse.of(response));
	}

	public ResponseEntity<?> reissue(String token) {
		if (jwtHelper.isRefreshTokenExpiredToken(token)) {
			User user = userRepository.findByRefreshToken(token)
				.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

			TokenDto tokenDto = jwtHelper.generateToken(user.getEmail());
			user.updateRefreshToken(tokenDto.getRefreshToken());
			return ResponseEntity.ok(SuccessResponse.of(tokenDto));
		} else {
			throw new UserException(UserErrorCode.USER_NOT_FOUND);
		}
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