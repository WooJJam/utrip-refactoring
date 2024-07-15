package woojjam.utrip.domains.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woojjam.utrip.common.exception.ErrorCausedBy;
import woojjam.utrip.common.exception.ReasonCode;
import woojjam.utrip.common.exception.StatusCode;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

	USER_NOT_FOUND(StatusCode.NOT_FOUND, ReasonCode.REQUESTED_RESOURCE_NOT_FOUND, "유저가 존재하지 않습니다."); // 4040

	private final StatusCode statusCode;
	private final ReasonCode reasonCode;
	private final String message;

	public ErrorCausedBy causedBy() {
		return ErrorCausedBy.of(statusCode, reasonCode);
	}
}
