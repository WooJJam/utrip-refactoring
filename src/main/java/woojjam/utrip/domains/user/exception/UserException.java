package woojjam.utrip.domains.user.exception;

import lombok.Getter;
import woojjam.utrip.common.exception.ErrorCausedBy;

@Getter
public class UserException extends RuntimeException {

	private final UserErrorCode userErrorCode;

	public UserException(UserErrorCode userErrorCode) {
		this.userErrorCode = userErrorCode;
	}

	public ErrorCausedBy errorCausedBy() {
		return userErrorCode.causedBy();
	}

	public String explainErrorMessage() {
		return userErrorCode.getMessage();
	}
}
