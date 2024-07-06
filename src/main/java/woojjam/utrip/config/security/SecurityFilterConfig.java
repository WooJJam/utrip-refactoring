package woojjam.utrip.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.security.CustomUserDetailsService;
import woojjam.utrip.common.security.filter.JwtAuthenticationFilter;
import woojjam.utrip.common.security.filter.JwtExceptionFilter;
import woojjam.utrip.common.security.jwt.JwtProvider;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityFilterConfig {

	private final CustomUserDetailsService userDetailsService;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(userDetailsService, jwtProvider);
	}

	@Bean
	public JwtExceptionFilter jwtExceptionFilter() {
		return new JwtExceptionFilter(objectMapper);
	}
}
