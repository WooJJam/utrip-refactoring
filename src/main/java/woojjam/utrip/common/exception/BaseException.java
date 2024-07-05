package woojjam.utrip.common.exception;

import lombok.Getter;
import woojjam.utrip.common.reponse.StatusCode;

@Getter
public class BaseException extends java.lang.RuntimeException {

	private final String status;

	public BaseException(StatusCode statusCode) {
		super(statusCode.getMessage());
		this.status = statusCode.getCode();
	}
}
