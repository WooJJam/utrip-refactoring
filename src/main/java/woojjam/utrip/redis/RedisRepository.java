package woojjam.utrip.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<Redis, Long> {

	Redis findByName(String name);
}
