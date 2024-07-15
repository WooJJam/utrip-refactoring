package woojjam.utrip.domains.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woojjam.utrip.common.exception.BaseErrorCode;
import woojjam.utrip.common.exception.ErrorCausedBy;
import woojjam.utrip.common.exception.ReasonCode;
import woojjam.utrip.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

	/**
	 * 404 Not Found Error Codes
	 */
	USER_NOT_FOUND(StatusCode.NOT_FOUND, ReasonCode.REQUESTED_RESOURCE_NOT_FOUND, "유저가 존재하지 않습니다."), // 4040

	/**
	 * 409 Conflict Error Codes
	 */

	EMAIL_ALREADY_EXISTS(StatusCode.CONFLICT, ReasonCode.RESOURCE_ALREADY_EXISTS, "중복된 이메일입니다."); // 4091

	private final StatusCode statusCode;
	private final ReasonCode reasonCode;
	private final String message;

	@Override
	public ErrorCausedBy causedBy() {
		return ErrorCausedBy.of(statusCode, reasonCode);
	}

	@Override
	public String getErrorMessage() {
		return message;
	}
}
