package woojjam.utrip.domains.auth.dto;

import lombok.Builder;
import lombok.Data;
import woojjam.utrip.domains.user.domain.User;

@Data
@Builder
public class LoginResponse {

	private Long id;
	private String nickname;
	private String email;
	private TokenDto token;

	public static LoginResponse of(User user, TokenDto tokenDto) {
		return LoginResponse.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.email(user.getEmail())
			.token(tokenDto)
			.build();
	}
}
