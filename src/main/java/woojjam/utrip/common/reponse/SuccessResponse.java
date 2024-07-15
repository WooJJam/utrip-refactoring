package woojjam.utrip.common.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woojjam.utrip.common.exception.StatusCode;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

	private int status;
	private String message;
	private T data;

	public static <T> SuccessResponse<T> of(StatusCode statusCode) {
		return of(statusCode.getCode(), "message", null);
	}

	public static <T> SuccessResponse<T> of(StatusCode statusCode, T data) {
		return of(statusCode.getCode(), "message", data);
	}

	public static <T> SuccessResponse<T> of(int status, String message, T data) {
		return SuccessResponse.<T>builder()
			.data(data)
			.status(status)
			.message(message)
			.build();
	}
}
