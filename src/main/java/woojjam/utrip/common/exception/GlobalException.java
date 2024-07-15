package woojjam.utrip.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

	private StatusCode statusCode;
	private ReasonCode reasonCode;
	private String message;

	public GlobalException(StatusCode statusCode, ReasonCode reasonCode) {
		super(statusCode);
		this.statusCode = statusCode;
		this.reasonCode = reasonCode;
		System.out.println("reasonCode+statusCode = " + reasonCode + statusCode);
	}
}
