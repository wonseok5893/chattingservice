package com.playground.th.service;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.controller.dto.ResponseMyProfileDto;
import com.playground.th.controller.dto.responseDto.ResponseChatRoomDto;
import com.playground.th.controller.dto.responseDto.ResponseDto;
import com.playground.th.domain.ImageFile;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.repository.MemberCustomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamRepository;
import com.playground.th.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;
    private final TeamRepository teamRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Transactional
    public Member join(MemberDto memberDto) {
        if (validateDuplicateMember(memberDto.getEmail())) {
            Member member = Member.create(memberDto);
            Member savedMember = memberRepository.save(member);
            return savedMember;
        } else
            return null;
    }

    private boolean validateDuplicateMember(String email) {
        //Exception
        try {
            Member existMember = memberRepository.findByEmail(email).get();
            return false;
        }catch(NoSuchElementException e){
            return true;
        }
    }
    @Transactional
    public Member login(MemberDto memberDto) {
        try {
            Member member = memberRepository.findByEmail(memberDto.getEmail()).get();
            if (passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) {
                if(!member.getToken().equals(memberDto.getFcmToken())||member.getToken()==null){
                    member.setToken(memberDto.getFcmToken());
                }
                return member;
            }
        }catch(NoSuchElementException e) {
            return null;
        }
        return null;
    }


    public Set<Team> findAllMyTeams(String email) {

        Member member = memberRepository.findByEmail(email).get();

        return member.getGroups();

    }

    public Member findByEmailToProfile(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail).get();
        return member;
    }
    @Transactional
    public ResponseDto loginAgain(String userEmail, String fcmToken) {
        try {
            Member member = memberRepository.findByEmail(userEmail).get();
            if(member==null)throw new UserNotFoundException(userEmail);
            if (!member.getToken().equals(fcmToken)||member.getToken()==null) {
                member.setToken(fcmToken);
            }
            return new ResponseDto(1, "자동 로그인 성공");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseDto(0,"로그인 실패");
        }
    }
}
