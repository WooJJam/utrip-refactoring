package woojjam.utrip.auth.dto;

import lombok.*;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SocialUserInfoDto {
    private String nickName;
    private String email;
    private String accessToken;
    private String refreshToken;

    public static SocialUserInfoDto of(String nickName, String email, String accessToken, String refreshToken) {
        return SocialUserInfoDto.builder()
                .nickName(nickName)
                .email(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}