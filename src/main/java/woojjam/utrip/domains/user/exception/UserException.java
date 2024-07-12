package woojjam.utrip.domains.user.exception;

import lombok.Getter;
import woojjam.utrip.common.exception.ReasonCode;
import woojjam.utrip.common.exception.StatusCode;

@Getter
public class UserException extends RuntimeException {

	private StatusCode statusCode;
	private ReasonCode reasonCode;
	private String message;

	public UserException(UserErrorCode userErrorCode) {
		this.statusCode = userErrorCode.getStatusCode();
		this.reasonCode = userErrorCode.getReasonCode();
		this.message = userErrorCode.getMessage();
		System.out.println("reasonCode+statusCode = " + reasonCode + statusCode);
	}
}
