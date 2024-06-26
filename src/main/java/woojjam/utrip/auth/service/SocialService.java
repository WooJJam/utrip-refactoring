package woojjam.utrip.auth.service;

import woojjam.utrip.auth.dto.LoginResponse;
import woojjam.utrip.auth.dto.NaverUserInfoDto;
import woojjam.utrip.auth.dto.TokenDto;
import woojjam.utrip.auth.dto.KakaoUserInfoDto;
import woojjam.utrip.common.exception.RuntimeException;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.JwtUtils;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.user.domain.User;
import woojjam.utrip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class SocialService {

    @Value("${KAKAO.REDIRECT.URI}")
    private String redirectUri;
    @Value("${KAKAO.REST.API.KEY}")
    private String restAPiKey;
    @Value("${NAVER.CLIENT.ID}")
    private String naverClientId;
    @Value("${NAVER.CLIENT.SECRET}")
    private String naverClientSecret;
    @Value("${NAVER.REDIRECT.URI}")
    private String naverRedirectUri;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    public ResponseEntity<?> kakaoLogin(String code) {
        KakaoUserInfoDto kakaoUserInfo = getKakaoAccessToken(code);
        User user = new User();
        Optional<User> findUser = userRepository.findByEmail(kakaoUserInfo.getEmail());
        TokenDto tokenDto = new TokenDto(kakaoUserInfo.getAccessToken(), kakaoUserInfo.getRefreshToken());
        if (findUser.isPresent()) {
            findUser.get().updateRefreshToken(kakaoUserInfo.getRefreshToken());
            LoginResponse response = LoginResponse.of(findUser.get(), tokenDto);
            return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(),response));
        } else {
            user.createUser(
                    kakaoUserInfo.getNickName(),
                    kakaoUserInfo.getEmail(),
                    null,
                    kakaoUserInfo.getRefreshToken()
            );
            userRepository.save(user);

            LoginResponse response = LoginResponse.of(user, tokenDto);
            return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(),response));
        }
    }

    private KakaoUserInfoDto getKakaoAccessToken(String code) {
        log.info("code = {}", code);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("client_id", restAPiKey);
            body.add("redirect_uri", redirectUri);
            body.add("code", code);

            HttpEntity<MultiValueMap<String, String>> kakaoRequest = new HttpEntity<>(body, headers);
            log.info("KAKAO REQUEST = {}", kakaoRequest);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> kakaoToken = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoRequest,
                    String.class
            );
            System.out.println("kakaoToken = " + kakaoToken.getBody());
            log.info("KAKAO TOKEN ={}", kakaoToken);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(kakaoToken.getBody());

            TokenDto tokenDto = new TokenDto(
                    (String) jsonObject.get("access_token"),
                    (String) jsonObject.get("refresh_token"));

            return getKakaoUserInfo(tokenDto);

        } catch (ParseException e) {
            throw new RuntimeException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }


    private KakaoUserInfoDto getKakaoUserInfo(TokenDto tokenDto) throws ParseException {
        log.info("KAKAO TOKEN DTO = {}", tokenDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenDto.getAccessToken());
        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=utf-8"));
        HttpEntity body = new HttpEntity(null, headers);

        log.info("Get Kakao User Info API Http Body = {}", body);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> kakaoUser = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                body,
                String.class
        );
        log.info("KAKAO USER = {}", kakaoUser);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(kakaoUser.getBody());
        JSONObject kakaoAccount = (JSONObject) jsonObject.get("kakao_account");
        JSONObject profile = (JSONObject) kakaoAccount.get("profile");
        String nickname = String.valueOf(profile.get("nickname"));
        String email = String.valueOf(kakaoAccount.get("email"));
        String accessToken = jwtUtils.generateToken(email, 1000 * 60 * 60, "AccessToken");
        String refreshToken = jwtUtils.generateToken(email, 1000 * 60 * 60 * 24, "RefreshToken");
        log.info("Nickname = {}, email = {}, accessToken = {}, refreshToken = {}", nickname, email, accessToken, refreshToken);

        return new KakaoUserInfoDto(nickname, email, accessToken, refreshToken);
    }

    public ResponseEntity<?> naverLogin(String code) {
        NaverUserInfoDto naverUserInfo = getNaverAccessToken(code);
        User user = new User();
        Optional<User> findUser = userRepository.findByEmail(naverUserInfo.getEmail());
        TokenDto tokenDto = new TokenDto(naverUserInfo.getAccessToken(), naverUserInfo.getRefreshToken());
        if (findUser.isPresent()) {
            findUser.get().updateRefreshToken(user.getRefreshToken());
            LoginResponse response = LoginResponse.of(findUser.get(), tokenDto);
            return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(),response));
        } else {
            user.createUser(
                    naverUserInfo.getNickName(),
                    naverUserInfo.getEmail(),
                    null,
                    naverUserInfo.getRefreshToken()
            );
            LoginResponse response = LoginResponse.of(user, tokenDto);
            return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(),response));
        }
    }

    private NaverUserInfoDto getNaverAccessToken(String code) {
        log.info("NAVER code = {}", code);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", naverClientId); // 네이버 클라이언트 ID를 설정 파일에서 불러와야 합니다.
            params.add("client_secret", naverClientSecret); // 네이버 클라이언트 시크릿을 설정 파일에서 불러와야 합니다.
            params.add("redirect_uri", naverRedirectUri); // 네이버 리다이렉트 URI를 설정 파일에서 불러와야 합니다.
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> naverRequest = new HttpEntity<>(params, headers);
            log.info("NAVER REQUEST = {}", naverRequest);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> naverTokenResponse = restTemplate.exchange(
                    "https://nid.naver.com/oauth2.0/token",
                    HttpMethod.POST,
                    naverRequest,
                    String.class
            );

            log.info("NAVER TOKEN = {}", naverTokenResponse.getBody());

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(naverTokenResponse.getBody());

            TokenDto tokenDto = new TokenDto(
                    (String) jsonObject.get("access_token"),
                    (String) jsonObject.get("refresh_token"));

            return getNaverUserInfo(tokenDto);

        } catch (ParseException e) {
            throw new RuntimeException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
    private NaverUserInfoDto getNaverUserInfo(TokenDto tokenDto) throws ParseException {
        // 로직의 대부분은 유지되지만, NaverUserInfoDto 생성 부분을 수정해야 합니다.
        log.info("NAVER TOKEN DTO = {}", tokenDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenDto.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<?> body = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> naverUserResponse = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                body,
                String.class
        );

        log.info("NAVER USER = {}", naverUserResponse.getBody());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(naverUserResponse.getBody());
        JSONObject response = (JSONObject) jsonObject.get("response");

        String nickname = (String) response.get("nickname");
        String email = (String) response.get("email");
        String accessToken = jwtUtils.generateToken(email, 1000 * 60 * 60, "AccessToken");
        String refreshToken = jwtUtils.generateToken(email, 1000 * 60 * 60 * 24, "RefreshToken");

        log.info("Nickname = {}, email = {}, accessToken = {}, refreshToken = {}", nickname, email, accessToken, refreshToken);

        return new NaverUserInfoDto(nickname, email, accessToken, refreshToken);
    }
}