package woojjam.utrip.domains.auth.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import woojjam.utrip.common.exception.BaseErrorCode;
import woojjam.utrip.common.exception.ErrorCausedBy;
import woojjam.utrip.common.exception.ReasonCode;
import woojjam.utrip.common.exception.StatusCode;

@RequiredArgsConstructor
@Getter
public enum JwtErrorCode implements BaseErrorCode {

	TOKEN_IS_EXPIRE(StatusCode.UNAUTHORIZED, ReasonCode.EXPIRED_OR_REVOKED_AUTHENTICATION_TOKEN, "토큰이 만료되었습니다.");

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
