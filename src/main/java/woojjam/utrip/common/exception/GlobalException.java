package woojjam.utrip.common.exception;

import lombok.Getter;

@Getter
public class GlobalException extends java.lang.RuntimeException {

	private final BaseErrorCode baseErrorCode;

	public GlobalException(BaseErrorCode baseErrorCore) {
		this.baseErrorCode = baseErrorCore;
	}

	public ErrorCausedBy errorCausedBy() {
		return baseErrorCode.causedBy();
	}

	public String errorMessage() {
		return baseErrorCode.getErrorMessage();
	}
}
