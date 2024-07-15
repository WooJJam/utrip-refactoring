package woojjam.utrip.domains.auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.domains.auth.dto.request.ChangePasswordRequest;
import woojjam.utrip.domains.auth.dto.request.LocalLoginRequest;
import woojjam.utrip.domains.auth.dto.request.RegisterRequest;
import woojjam.utrip.domains.auth.service.AuthService;
import woojjam.utrip.domains.auth.service.SocialService;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final SocialService socialService;

	@PostMapping("/kakao/login")
	@PreAuthorize("isAnonymous()")
	public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> request) {
		String code = request.get("code");
		return socialService.kakaoLogin(code);
	}

	@PostMapping("/register")
	@PreAuthorize("isAnonymous()")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		return authService.register(registerRequest);
	}

	@PostMapping("/duplicate/email")
	@PreAuthorize("isAnonymous()")
	public ResponseEntity<?> checkEmailDuplicate(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		log.info("email = {}", email);
		return authService.checkEmailDuplicate(email);
	}

	@PostMapping("/login")
	@PreAuthorize("isAnonymous()")
	public ResponseEntity<?> localLogin(@RequestBody LocalLoginRequest localLoginRequest) {
		return authService.localLogin(localLoginRequest);
	}

	@PatchMapping("/reissue")
	public ResponseEntity<?> reissue(@RequestBody Map<String, String> body) {
		String refreshToken = body.get("refresh_token");
		return authService.reissue(refreshToken);
	}

	@PostMapping("/naver/login")
	@PreAuthorize("isAnonymous()")
	public ResponseEntity<?> naverLogin(@RequestBody Map<String, String> request) {
		String code = request.get("code");
		return socialService.naverLogin(code);
	}

	@PostMapping("/find-passowrd")
	public ResponseEntity<?> findPassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		// if (!authService.isUserEmail(email)) {
		// 	throw new UserException(StatusCode.USER_NOT_FOUND);
		// }
		return ResponseEntity.ok(SuccessResponse.noContent());
	}

	@PatchMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		authService.changePassword(changePasswordRequest);
		return ResponseEntity.ok(SuccessResponse.noContent());
	}
}
