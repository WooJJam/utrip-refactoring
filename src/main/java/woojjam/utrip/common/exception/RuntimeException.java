package woojjam.utrip.common.exception;

import lombok.Getter;
import woojjam.utrip.common.reponse.StatusCode;

@Getter
public class RuntimeException extends BaseException {
	public RuntimeException(StatusCode statusCode) {
		super(statusCode);
	}
}
