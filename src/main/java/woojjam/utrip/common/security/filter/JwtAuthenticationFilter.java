package woojjam.utrip.common.security.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.TokenException;
import woojjam.utrip.common.security.authentication.CustomUserDetailsService;
import woojjam.utrip.common.security.jwt.JwtProvider;
import woojjam.utrip.domains.auth.exception.JwtErrorCode;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService userDetailsService;
	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		System.out.println(" ================ ");
		if (isAnonymous(request)) {
			log.info("Anonymous Request");
			filterChain.doFilter(request, response);
			return;
		}

		String token = resolveToken(request, response);

		String email = getTokenClaims(token);
		log.debug("email : {}", email);

		UserDetails userDetails = getUserDetails(email);
		log.debug("userDetails : {}", userDetails);

		authenticate(userDetails);

		filterChain.doFilter(request, response);
	}

	/**
	 * Token이 비어있다면 익명 사용자
	 *
	 * @param request
	 * @return Boolean
	 * @throws ServletException
	 */
	private boolean isAnonymous(HttpServletRequest request) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		return accessToken == null;
	}

	/**
	 * 토큰 정보가 유효한지 검사
	 *
	 * @param request
	 * @param response
	 * @return boolean
	 * @throws ServletException
	 * @throws IOException
	 */
	private String resolveToken(HttpServletRequest request, HttpServletResponse response) throws
		ServletException {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = jwtProvider.resolveToken(header);

		// if (!StringUtils.hasText(token)) {
		// 	log.warn("token not found");
		// 	handleException(StatusCode.TOKEN_IS_NULL);
		// }

		if (jwtProvider.isTokenExpired(token)) {
			log.warn("token is expired");
			handleException(JwtErrorCode.TOKEN_IS_EXPIRE);
		}

		return token;
	}

	private String getTokenClaims(String token) {
		Claims claims = jwtProvider.getClaims(token);
		return claims.get("email", String.class);
	}

	private UserDetails getUserDetails(String email) {
		return userDetailsService.loadUserByUsername(email);
	}

	private void authenticate(UserDetails userDetails) {
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private void handleException(JwtErrorCode error) throws ServletException {
		log.error("JwtAuthException = {}, {}", error.name(), error.getErrorMessage());
		TokenException tokenException = new TokenException(error);
		throw new ServletException(tokenException);
	}
}
