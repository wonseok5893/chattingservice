package com.playground.th.service;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.domain.Member;
import com.playground.th.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public boolean join(MemberDto memberDto) {
        Member newMember = Member.create(memberDto.getEmail(), memberDto.getPassword());
        if(validateDuplicateMember(memberDto.getEmail())) {
            memberRepository.save(newMember);
        }else return false;
        return true;
    }
    private boolean validateDuplicateMember(String email) {
        //Exception
        Member existMember = memberRepository.findByEmail(email);
        if(existMember!=null){
            return false;
        }
        return true;
    }
}
