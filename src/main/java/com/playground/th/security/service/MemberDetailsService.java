package com.playground.th.security.service;

import com.playground.th.domain.MemberDetails;
import com.playground.th.repository.MemberRepository;
import com.playground.th.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service("userDetailsService")
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).map(u -> new MemberDetails(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue())))).orElseThrow(() -> new UserNotFoundException(email));
    }
}
