package CodeIt.utrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class YtripApplication {

	public static void main(String[] args) {
		SpringApplication.run(YtripApplication.class, args);
	}

}
