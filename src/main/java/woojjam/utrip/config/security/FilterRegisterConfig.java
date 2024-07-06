package woojjam.utrip.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.security.filter.JwtAuthenticationFilter;
import woojjam.utrip.common.security.filter.JwtExceptionFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FilterRegisterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtExceptionFilter jwtExceptionFilter;

	@Override
	public void configure(HttpSecurity http) {
		http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
		http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
	}
}
