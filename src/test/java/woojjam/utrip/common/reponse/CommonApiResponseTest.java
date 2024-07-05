package woojjam.utrip.common.reponse;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CommonApiResponseTest {

	@Test
	public void 공통_응답_생성() throws Exception {
		ResponseEntity<?> test = ResponseEntity.ok(
			SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
		System.out.println("test = " + test);
	}

}
