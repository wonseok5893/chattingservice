package com.playground.th;

import com.playground.th.security.interceptor.JwtTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 작성한 인터셉터를 추가한다.
        registry.addInterceptor(jwtTokenInterceptor())
                // 예제의 경우 전체 사용자를 조회하는 /user/findAll 에 대해 토큰 검사를 진행한다.
                .addPathPatterns("/my/chatRooms")
                .addPathPatterns("/member/login/again");
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }
}
