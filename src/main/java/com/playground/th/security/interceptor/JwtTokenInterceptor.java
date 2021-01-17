package com.playground.th.security.interceptor;

import com.playground.th.security.AuthConstants;
import com.playground.th.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String header = request.getHeader(AuthConstants.AUTH_HEADER);
        if (header != null) {
            String token = JwtUtil.getTokenFromHeader(header);
            if (JwtUtil.isValidToken(token)) {
                return true;
            }
        }
        response.sendRedirect("/error/unauthorized");
        return false;

    }

}