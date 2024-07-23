package woojjam.utrip.redis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woojjam.utrip.common.reponse.SuccessResponse;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {

	private final RedisService redisService;

	public RedisController(RedisService redisService) {
		this.redisService = redisService;
	}

	/**
	 * Redis의 값을 조회합니다.
	 *
	 * @param redisDto
	 * @return
	 */
	@GetMapping("/getValue")
	public ResponseEntity<?> getValue(@RequestBody RedisDto redisDto) {
		String result = redisService.getValue(redisDto.getKey());

		return ResponseEntity.ok(SuccessResponse.of(result));
	}

	/**
	 * Redis의 값을 추가/수정합니다.
	 *
	 * @param redisDto
	 * @return
	 */
	@PostMapping("/setValue")
	public ResponseEntity<?> setValue(@RequestBody RedisDto redisDto) {

		if (redisDto.getDuration() == null) {
			redisService.setValues(redisDto.getKey(), redisDto.getValue());
		} else {
			redisService.setValues(redisDto.getKey(), redisDto.getValue(), redisDto.getDuration());
		}

		return ResponseEntity.ok(SuccessResponse.noContent());
	}

	/**
	 * Redis의 key 값을 기반으로 row를 제거합니다.
	 *
	 * @param redisDto
	 * @return
	 */
	@PostMapping("/deleteValue")
	public ResponseEntity<?> deleteRow(@RequestBody RedisDto redisDto) {
		redisService.deleteValue(redisDto.getKey());

		return ResponseEntity.ok(SuccessResponse.noContent());
	}

	@PostMapping("/setName")
	public ResponseEntity<?> setName(@RequestBody RedisDto redisDto) {
		String s = redisService.setName(redisDto.getName());
		return ResponseEntity.ok(SuccessResponse.of(s));
	}

	@GetMapping("/getName")
	public ResponseEntity<?> getName(@RequestBody RedisDto redisDto) {
		Redis redis = redisService.getName(redisDto.getName());
		return ResponseEntity.ok(SuccessResponse.of(redis));
	}
}
