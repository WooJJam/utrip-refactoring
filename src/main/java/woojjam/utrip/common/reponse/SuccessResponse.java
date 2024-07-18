package woojjam.utrip.common.reponse;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

	private final String status = "2000";
	private final String message = "요청에 성공하였습니다.";
	private T data;

	@Builder
	public SuccessResponse(T data) {
		this.data = data;
	}

	public static SuccessResponse noContent() {
		return SuccessResponse.builder()
			.data(Map.of())
			.build();
	}

	public static <T> SuccessResponse<T> of(T data) {
		return SuccessResponse.<T>builder()
			.data(data)
			.build();
	}
}
