package woojjam.utrip.redis;

import java.time.Duration;

public interface RedisService {
	void setValues(String key, String value); // 값 등록 / 수정

	void setValues(String key, String value, Duration duration); // 값 등록 / 수정

	String getValue(String key); // 값 조회

	void deleteValue(String key); // 값 삭제

	String setName(String name);

	Redis getName(String name);
}
