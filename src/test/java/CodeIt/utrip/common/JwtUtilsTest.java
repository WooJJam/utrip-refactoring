package CodeIt.utrip.common;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {

    @Autowired
    JwtUtils jwtUtils;
    
    @Test
    public void 토큰_생성() throws Exception {
        String s = jwtUtils.generateToken("test", 1000 * 60 * 60, "AccessToken");
        Assertions.assertNotNull(s);
        System.out.println("s = " + s);
    }
    
    @Test
    public void 토큰_복호화() {
        String token = jwtUtils.generateToken("test", 1000 * 60 * 60, "AccessToken");
        Claims claims = jwtUtils.getClaims(token);
        assertThat(claims.get("email")).isEqualTo("test");
    }

}