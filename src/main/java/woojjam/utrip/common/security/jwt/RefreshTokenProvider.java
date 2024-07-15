package woojjam.utrip.common.security.jwt;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.TokenException;

@Slf4j
@Component
public class RefreshTokenProvider implements JwtProvider {

	private final SecretKey secretKey;
	private final Duration refreshTokenExpiration;

	public RefreshTokenProvider(
		@Value("${jwt.secret.refresh-token}") String secretKey,
		@Value("${jwt.expiration-time.refresh-token}") Duration refreshTokenExpiration) {
		this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	@Override
	public String generateToken(String email) {
		Date now = new Date();
		return Jwts.builder()
			.claims(createClaims(email))
			.expiration(createExpireDate(now, refreshTokenExpiration.toMillis()))
			.signWith(secretKey)
			.compact();
	}

	@Override
	public Map<String, Object> createClaims(String email) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", email);
		return claims;
	}

	@Override
	public Date createExpireDate(final Date date, Long expirationTime) {
		return new Date(date.getTime() + expirationTime);
	}

	@Override
	public Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	@Override
	public boolean isTokenExpired(String token) {
		try {
			Claims claims = getClaims(token);
			return claims.getExpiration().before(new Date());
		} catch (TokenException e) {
			// if (StatusCode.TOKEN_EXPIRED.getCode().equals(e.getStatus())) {
			// 	return true;
			// }
			throw e;
		}
	}

}
