package woojjam.utrip.redis;

import java.time.Duration;

import lombok.Data;

@Data
public class RedisDto {
	private String key;
	private String value;
	private String name;
	private Duration duration;
}
