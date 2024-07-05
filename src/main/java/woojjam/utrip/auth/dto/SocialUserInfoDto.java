package woojjam.utrip.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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