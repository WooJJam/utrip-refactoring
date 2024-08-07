package woojjam.utrip.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends java.lang.RuntimeException {

	private final int status;

	public BaseException(StatusCode statusCode) {
		super("statusCode.getMessage()");
		this.status = statusCode.getCode();
	}
}
