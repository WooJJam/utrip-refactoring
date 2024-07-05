package woojjam.utrip.common.security.jwt;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.TokenException;
import woojjam.utrip.common.reponse.StatusCode;

@Slf4j
@Component
@Primary
public class AccessTokenProvider implements JwtProvider {

	private final SecretKey secretKey;
	private final Duration accessTokenExpiration;

	public AccessTokenProvider(
		@Value("${jwt.secret.access-token}") String secretKey,
		@Value("${jwt.expiration-time.access-token}") Duration accessTokenExpiration) {
		this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
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
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	@Override
	public boolean isValidToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (ExpiredJwtException exception) {
			log.error("token Expired");
			throw new TokenException(StatusCode.TOKEN_EXPIRED);
		} catch (JwtException exception) {
			log.error("Token Tampered");
			throw new TokenException(StatusCode.TOKEN_IS_TAMPERED);
		} catch (NullPointerException exception) {
			log.error("Token is Null");
			throw new TokenException(StatusCode.TOKEN_IS_NULL);
		}
	}
}
