package woojjam.utrip.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {
	SUCCESS(200),
	BAD_REQUEST(400),
	UNAUTHORIZED(401),
	FORBIDDEN(403),
	NOT_FOUND(404),
	METHOD_NOT_ALLOWED(405),
	NOT_ACCEPTABLE(406),
	CONFLICT(409),
	PRECONDITION_FAILED(412),
	UNPROCESSABLE_CONTENT(422),
	INTERNAL_SERVER_ERROR(500);
	
	private final int code;
}
