package woojjam.utrip.common.exception;

import lombok.Getter;
import woojjam.utrip.common.reponse.StatusCode;

@Getter
public class NoSuchElementException extends BaseException {
	public NoSuchElementException(StatusCode statusCode) {
		super(statusCode);
	}
}
