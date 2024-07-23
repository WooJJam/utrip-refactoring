package woojjam.utrip.redis;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

	private final RedisTemplate<String, String> redisTemplate;
	private final RedisRepository redisRepository;

	@Override
	public void setValues(String key, String value) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set(key, value);
	}

	@Override
	public void setValues(String key, String value, Duration duration) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set(key, value, duration);
	}

	@Override
	public String getValue(String key) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		if (values.get(key) == null) {
			return null;
		}
		return String.valueOf(values.get(key));
	}

	@Override
	public void deleteValue(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public String setName(String name) {
		Redis redis = Redis.builder()
			.id(4L)
			.name(name)
			.build();
		return redisRepository.save(redis).getName();
	}

	@Override
	public Redis getName(String name) {
		Redis find = redisRepository.findByName(name);
		Optional<Redis> findById = redisRepository.findById(1L);
		Iterable<Redis> all = redisRepository.findAll();
		for (Redis redis : all) {
			System.out.println("redis.toString() = " + redis.toString());
		}
		return find;
	}
}
