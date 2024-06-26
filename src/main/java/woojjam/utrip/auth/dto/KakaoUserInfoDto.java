package woojjam.utrip.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class KakaoUserInfoDto {
    private String nickName;
    private String email;
    private String accessToken;
    private String refreshToken;
}