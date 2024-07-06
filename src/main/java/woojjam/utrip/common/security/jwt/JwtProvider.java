package woojjam.utrip.common.security.jwt;

import java.util.Date;
import java.util.Map;

import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;

public interface JwtProvider {

	default String resolveToken(String token) {
		if (StringUtils.hasText(token) && token.startsWith("Bearer")) {
			return token.substring(7);
		}
		return "";
	}

	String generateToken(String email);

	Map<String, Object> createClaims(String email);

	Date createExpireDate(final Date date, final Long expirationTime);

	Claims getClaims(String token);

	boolean isTokenExpired(String token);
}
