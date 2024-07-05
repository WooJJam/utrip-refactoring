package woojjam.utrip.common.security.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.reponse.ErrorResponse;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.security.CustomUserDetailsService;
import woojjam.utrip.common.security.jwt.JwtProvider;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService userDetailsService;
	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		if (isAnonymous(request, response)) {
			log.info("Anonymous Request");
			filterChain.doFilter(request, response);
			return;
		}

		String token = jwtProvider.resolveToken(request.getHeader(HttpHeaders.AUTHORIZATION));

		if (isValidToken(response, token)) {
			return;
		}

		String email = jwtProvider.getClaims(token).get("email", String.class);
		log.debug("email : {}", email);

		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		filterChain.doFilter(request, response);
	}

	/*
	 * 만약 아무런 토큰이 없다면 익명 사용자
	 */
	private boolean isAnonymous(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		return accessToken == null;
	}

	private boolean isValidToken(HttpServletResponse response, String token) throws
		ServletException,
		IOException {

		if (!StringUtils.hasText(token)) {
			handleException(response, StatusCode.TOKEN_IS_NULL);
			return true;
		}

		if (jwtProvider.isTokenExpired(token)) {
			handleException(response, StatusCode.TOKEN_EXPIRED);
			return true;
		}

		return false;

	}

	private void handleException(HttpServletResponse response, StatusCode error) {
		log.error("JwtAuthException = {}, {}", error.getCode(), error.getMessage());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		try {
			String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(error.getCode(), error.getMessage()));
			response.getWriter().write(json);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
