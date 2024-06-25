package CodeIt.utrip.common.interceptor;

import CodeIt.utrip.common.JwtUtils;
import CodeIt.utrip.common.reponse.StatusCode;
import CodeIt.utrip.common.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        String token = jwtUtils.splitBearerToken(request.getHeader("Authorization"));
        if (jwtUtils.isValidToken(token)) {
            log.info("AccessToken = {}", token);
            return true;
        }
        throw new UserException(StatusCode.LOGIN_REQUIRED);
    }
}
