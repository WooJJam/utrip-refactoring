package woojjam.utrip.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonCode {

	/* 400 BAD_REQUEST */
	INVALID_REQUEST_SYNTAX(0),
	MISSING_REQUEST_PARAMETER(1),
	MALFORMED_PARAMETER(2),
	MALFORMED_REQUEST_BODY(3),
	INVALID_REQUEST(4),
	CLIENT_ERROR(5);

	private final int code;
}
