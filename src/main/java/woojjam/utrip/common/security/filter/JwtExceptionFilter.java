package woojjam.utrip.common.security.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.TokenException;
import woojjam.utrip.common.reponse.ErrorResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof TokenException) {
				sendJwtError(response, (TokenException)cause);
			}
		}
	}

	private void sendJwtError(HttpServletResponse response, TokenException e) throws
		IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		ErrorResponse errorResponse = ErrorResponse.of(e.errorCausedBy().getCode(), e.errorMessage());
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}
