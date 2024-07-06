package woojjam.utrip.common;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Claims;
import woojjam.utrip.common.security.jwt.AccessTokenProvider;

@SpringBootTest
class JwtUtilsTest {

	@Autowired
	AccessTokenProvider jwtUtils;

	@Test
	public void 토큰_생성() throws Exception {
		String s = jwtUtils.generateToken("test");
		Assertions.assertNotNull(s);
		System.out.println("s = " + s);
	}

	@Test
	public void 토큰_복호화() {
		String token = jwtUtils.generateToken("test");
		Claims claims = jwtUtils.getClaims(token);
		assertThat(claims.get("email")).isEqualTo("test");
	}

}