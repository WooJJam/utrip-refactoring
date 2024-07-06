package woojjam.utrip.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import woojjam.utrip.common.security.handler.JwtAccessDeniedHandler;
import woojjam.utrip.common.security.handler.JwtAuthenticationEntryPoint;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityConfig {

	private final ObjectMapper objectMapper;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint(objectMapper);
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new JwtAccessDeniedHandler(objectMapper);
	}
}
