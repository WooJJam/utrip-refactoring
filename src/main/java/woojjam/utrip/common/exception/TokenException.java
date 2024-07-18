package woojjam.utrip.common.exception;

import lombok.Getter;
import woojjam.utrip.domains.auth.exception.JwtErrorCode;

@Getter
public class TokenException extends GlobalException {

	private final JwtErrorCode jwtErrorCode;

	public TokenException(JwtErrorCode jwtErrorCode) {
		super(jwtErrorCode);
		this.jwtErrorCode = jwtErrorCode;
	}
}
