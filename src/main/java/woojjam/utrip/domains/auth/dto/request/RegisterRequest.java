package woojjam.utrip.domains.auth.dto.request;

import lombok.Data;
import woojjam.utrip.domains.user.domain.User;

@Data
public class RegisterRequest {

	String nickname;
	String email;
	String password;
	String checkPassword;
	String role;

	public static User toEntity(String nickname, String email, String password) {
		return User.builder()
			.nickname(nickname)
			.refreshToken(null)
			.email(email)
			.password(password)
			.build();
	}
}
