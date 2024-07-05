package woojjam.utrip.common.security.jwt;

import org.springframework.stereotype.Component;

import woojjam.utrip.common.exception.TokenException;
import woojjam.utrip.common.reponse.StatusCode;

@Component
public class JwtUtils {
	public String splitBearerToken(String bearerToken) {
		try {
			return bearerToken.split(" ")[1];
		} catch (NullPointerException e) {
			throw new TokenException(StatusCode.TOKEN_IS_NULL);
		}
	}
}
