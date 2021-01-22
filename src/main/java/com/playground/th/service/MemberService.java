package com.playground.th.service;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.controller.dto.responseDto.ResponseData;
import com.playground.th.controller.dto.responseDto.ResponseDto;
import com.playground.th.controller.dto.responseDto.member.ResponseMemberMyInfo;
import com.playground.th.controller.dto.responseDto.member.ResponseMyTeamInfo;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.exception.TokenNotExistException;
import com.playground.th.repository.MemberCustomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamRepository;
import com.playground.th.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    @Transactional
    public Member login(MemberDto memberDto) throws RuntimeException {
        try {
            Member member = memberRepository.findByEmail(memberDto.getEmail()).orElseThrow(() -> new UserNotFoundException(memberDto.getEmail()));
            if (passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) {
                if (memberDto.getFcmToken() != null) {
                    memberDto.setFcmToken(memberDto.getFcmToken().substring(1, memberDto.getFcmToken().length() - 1));
                    if (member.getToken() == null) {
                        member.setToken(memberDto.getFcmToken());
                    } else {
                        if (!member.getToken().equals(memberDto.getFcmToken())) {
                            member.setToken(memberDto.getFcmToken());
                        }
                    }
                }
            }
            return member;
        }catch(Exception e){
          e.printStackTrace();
          return null;
        }
    }


    public Set<Team> findAllMyTeams(String email) {
        Member member = memberRepository.findByEmail(email).get();
        return member.getGroups();

    }

    @Transactional
    public ResponseDto loginAgain(String userEmail, String fcmToken) throws RuntimeException {
        try {
            fcmToken =fcmToken.substring(1,fcmToken.length()-1);
            Member member = memberRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
            if (fcmToken == null) throw new TokenNotExistException();
            if (!member.getToken().equals(fcmToken) || member.getToken() == null) {
                member.setToken(fcmToken);
            }
            return new ResponseDto(member.getId(), "자동 로그인 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto(0L, "로그인 실패");
        }
    }

    public ResponseMemberMyInfo findMyInfo(String userEmail) {
        try {
            Member member = memberRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
            List<ResponseMyTeamInfo> teams = member.getGroups().stream().map((team) -> new ResponseMyTeamInfo(team)).collect(Collectors.toList());
            List<String> images = member.getImages().stream().map((image) -> image.getFileUrl()).collect(Collectors.toList());
        return new ResponseMemberMyInfo(member, images, teams);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ResponseMemberMyInfo findMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new UserNotFoundException(memberId));
        List<ResponseMyTeamInfo> teams = member.getGroups().stream().map((team) -> new ResponseMyTeamInfo(team)).collect(Collectors.toList());
        List<String> images = member.getImages().stream().map((image) -> image.getFileUrl()).collect(Collectors.toList());
        return new ResponseMemberMyInfo(member, images, teams);
    }
}
