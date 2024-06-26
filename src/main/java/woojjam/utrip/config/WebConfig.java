package woojjam.utrip.config;

import woojjam.utrip.common.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    /**
     * 로그인 인증 인가를 위한 인터셉터
     * 개발 단계에서는 주석 처리
     * 추후 배포 단계에서 주석 해제 필요
     */
    private final JwtInterceptor jwtInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/api/auth/**","/test/**");
//    }

    /**
     * CORS 설정
     * 현재 모든 리소스의 요청을 허용
     * 추후 프론트 서버만 요청 허용하도록 변경 필요
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("Authorization", "Content-Type")
                .exposedHeaders("Custom-Header")
//                .allowCredentials(true)
                .maxAge(3600);
    }
}

