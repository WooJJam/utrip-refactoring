package woojjam.utrip.common.exception;

import lombok.Getter;
import woojjam.utrip.common.reponse.StatusCode;

@Getter
public class TokenException extends BaseException {

	public TokenException(StatusCode statusCode) {
		super(statusCode);
	}
}
