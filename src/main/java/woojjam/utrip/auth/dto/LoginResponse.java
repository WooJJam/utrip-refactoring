package woojjam.utrip.auth.dto;

import woojjam.utrip.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Long id;
    private String nickname;
    private String email;
    private TokenDto token;

    public static LoginResponse of (User user, TokenDto tokenDto) {
        return LoginResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .token(tokenDto)
                .build();
    }
}
