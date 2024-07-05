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

import io.jsonwebtoken.Claims;
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

		if (!isValidToken(response, token)) {
			return;
		}

		String email = getTokenClaims(token);
		log.debug("email : {}", email);

		UserDetails userDetails = getUserDetails(email);
		log.debug("userDetails : {}", userDetails);

		authenticate(userDetails);

		filterChain.doFilter(request, response);
	}

	/**
	 * Token이 비어있다면 익명 사용자
	 * @param request
	 * @param response
	 * @return Boolean
	 * @throws ServletException
	 */
	private boolean isAnonymous(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		return accessToken == null;
	}

	/**
	 * 토큰 정보가 유효한지 검사
	 * @param response
	 * @param token
	 * @return boolean
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean isValidToken(HttpServletResponse response, String token) throws
		ServletException,
		IOException {

		if (!StringUtils.hasText(token)) {
			handleException(response, StatusCode.TOKEN_IS_NULL);
			return false;
		}

		if (jwtProvider.isTokenExpired(token)) {
			handleException(response, StatusCode.TOKEN_EXPIRED);
			return false;
		}

		return true;

	}

	private String getTokenClaims(String token) {
		Claims claims = jwtProvider.getClaims(token);
		return claims.get("email", String.class);
	}

	private UserDetails getUserDetails(String email) {
		return userDetailsService.loadUserByUsername(email);
	}

	private void authenticate(UserDetails userDetails) {
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
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
