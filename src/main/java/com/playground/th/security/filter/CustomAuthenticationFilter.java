package com.playground.th.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.th.controller.dto.MemberDto;
import com.playground.th.domain.Member;
import com.playground.th.security.exception.InputNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try{
            MemberDto memberDto = new ObjectMapper().readValue(request.getInputStream(), MemberDto.class);
            authRequest = new UsernamePasswordAuthenticationToken(memberDto.getEmail(), memberDto.getPassword());
        } catch (IOException exception){
            throw new InputNotFoundException();
        }
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
