package woojjam.utrip.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final AccessDeniedHandler jwtAccessDeniedHandler;
	private final FilterRegisterConfig filterRegisterConfig;

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
			.with(filterRegisterConfig, Customizer.withDefaults())
			.exceptionHandling(exception -> {
					exception.authenticationEntryPoint(authenticationEntryPoint);
					exception.accessDeniedHandler(jwtAccessDeniedHandler);
				}
			)
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

}
