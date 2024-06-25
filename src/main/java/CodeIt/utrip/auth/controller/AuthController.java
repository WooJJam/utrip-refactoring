package CodeIt.utrip.auth.controller;

import CodeIt.utrip.auth.dto.request.ChangePasswordRequest;
import CodeIt.utrip.auth.dto.request.LocalLoginRequest;
import CodeIt.utrip.auth.dto.request.RegisterRequest;
import CodeIt.utrip.auth.service.AuthService;
import CodeIt.utrip.auth.service.SocialService;
import CodeIt.utrip.common.JwtUtils;
import CodeIt.utrip.common.exception.UserException;
import CodeIt.utrip.common.reponse.StatusCode;
import CodeIt.utrip.common.reponse.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SocialService socialService;
    private final JwtUtils jwtUtils;
    @PostMapping("/kakao/login")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        return socialService.kakaoLogin(code);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/duplicate/email")
    public ResponseEntity<?> checkEmailDuplicate(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        log.info("email = {}", email);
        return authService.checkEmailDuplicate(email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> localLogin(@RequestBody LocalLoginRequest localLoginRequest) {
        return authService.localLogin(localLoginRequest);
    }

    @PatchMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refresh_token");
        return authService.reissue(refreshToken);
    }
    @PostMapping ("/naver/login")
    public ResponseEntity<?> naverLogin(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        return socialService.naverLogin(code);
    }

    @PostMapping("/find-passowrd")
    public ResponseEntity<?> findPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (!authService.isUserEmail(email)) {
            throw new UserException(StatusCode.USER_NOT_FOUND);
        }
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }
}
