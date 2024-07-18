package woojjam.utrip.common.exception;

import lombok.Getter;

@Getter
public class RuntimeException extends BaseException {
	public RuntimeException(StatusCode statusCode) {
		super(statusCode);
	}
}
