package woojjam.utrip.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.security.CustomUserDetailsService;
import woojjam.utrip.common.security.filter.JwtAuthenticationFilter;
import woojjam.utrip.common.security.jwt.AccessTokenProvider;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final AccessTokenProvider jwtProvider;
	private final CustomUserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("WebSecurityConfig Start");

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.authorizeHttpRequests(request -> {
				request.requestMatchers("/api/auth/**").permitAll();
				request.anyRequest().authenticated();
			})
			.addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtProvider),
				UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

}
