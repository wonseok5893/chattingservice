package com.playground.th.security.handler;

import com.playground.th.domain.Member;
import com.playground.th.domain.MemberDetails;
import com.playground.th.security.AuthConstants;
import com.playground.th.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        String token = JwtUtil.generateJwtToken(member);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
    }
}
