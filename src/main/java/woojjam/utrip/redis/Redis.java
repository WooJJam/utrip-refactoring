package woojjam.utrip.redis;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@RedisHash("redis")
@ToString(of = {"name"})
public class Redis {
	@Id
	private Long id;
	String name;
}
