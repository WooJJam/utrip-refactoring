package woojjam.utrip.common.exception;

import java.util.Objects;

public record ErrorCausedBy(StatusCode statusCode, ReasonCode reasonCode) {

	public ErrorCausedBy {
		Objects.requireNonNull(statusCode, "statusCode must not be null");
		Objects.requireNonNull(reasonCode, "reasonCode must not be null");
	}

	public static ErrorCausedBy of(StatusCode statusCode, ReasonCode reasonCode) {
		return new ErrorCausedBy(statusCode, reasonCode);
	}

	public String getCode() {
		return String.valueOf(statusCode.getCode() * 10 + reasonCode.getCode());
	}
}
