package woojjam.utrip.common.security.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.TokenException;
import woojjam.utrip.domains.auth.exception.JwtErrorCode;

@Slf4j
@Component
@Primary
public class AccessTokenProvider implements JwtProvider {

	private final SecretKey secretKey;
	private final Duration accessTokenExpiration;

	public AccessTokenProvider(
		@Value("${jwt.secret.access-token}") String secretKey,
		@Value("${jwt.expiration-time.access-token}") Duration accessTokenExpiration) {
		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
		this.accessTokenExpiration = accessTokenExpiration;
	}

	@Override
	public String generateToken(String email) {
		Date now = new Date();
		return Jwts.builder()
			.claims(createClaims(email))
			.expiration(createExpireDate(now, accessTokenExpiration.toMillis()))
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
	public Date createExpireDate(Date date, Long expirationTime) {
		return new Date(date.getTime() + expirationTime);
	}

	@Override
	public Claims getClaims(String token) {
		try {
			return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		} catch (JwtException e) {
			log.warn("getClaims = {}", e.getMessage());
			throw new TokenException(JwtErrorCode.TOKEN_IS_EXPIRE);
		}
	}

	@Override
	public boolean isTokenExpired(String token) {
		try {
			Claims claims = getClaims(token);
			return claims.getExpiration().before(new Date());
		} catch (TokenException e) {
			log.warn("isTokenExpired = {}", e.getJwtErrorCode().causedBy().getCode());
			if (JwtErrorCode.TOKEN_IS_EXPIRE.causedBy().getCode().equals(e.getJwtErrorCode().causedBy().getCode())) {
				return true;
			}
			throw e;
		}
	}
}
