package woojjam.utrip.domains.auth.helper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import woojjam.utrip.common.security.jwt.JwtProvider;
import woojjam.utrip.domains.auth.dto.TokenDto;

@Component
public class JwtHelper {

	private final JwtProvider accessTokenProvider;
	private final JwtProvider refreshTokenProvider;

	public JwtHelper(
		@Qualifier("accessTokenProvider") JwtProvider accessTokenProvider,
		@Qualifier("refreshTokenProvider") JwtProvider refreshTokenProvider) {
		this.accessTokenProvider = accessTokenProvider;
		this.refreshTokenProvider = refreshTokenProvider;
	}

	public TokenDto generateToken(String email) {
		String accessToken = accessTokenProvider.generateToken(email);
		String refreshToken = refreshTokenProvider.generateToken(email);
		return TokenDto.of(accessToken, refreshToken);
	}

	public boolean isAccessTokenExpiredToken(String token) {
		return accessTokenProvider.isTokenExpired(token);
	}

	public boolean isRefreshTokenExpiredToken(String token) {
		return refreshTokenProvider.isTokenExpired(token);
	}
}
