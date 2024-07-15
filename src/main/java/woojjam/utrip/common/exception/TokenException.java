package woojjam.utrip.common.exception;

import lombok.Getter;

@Getter
public class TokenException extends BaseException {

	public TokenException(StatusCode statusCode) {
		super(statusCode);
	}
}
