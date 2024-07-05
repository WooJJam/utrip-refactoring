package woojjam.utrip.common.security.jwt;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;

public interface JwtProvider {

	String generateToken(String email);

	Map<String, Object> createClaims(String email);

	boolean isValidToken(String token);

	Date createExpireDate(final Date date, final Long expirationTime);

	Claims getClaims(String token);
}
