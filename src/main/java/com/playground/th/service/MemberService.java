package com.playground.th.service;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.controller.dto.ResponseMyProfileDto;
import com.playground.th.controller.dto.responseDto.ResponseChatRoomDto;
import com.playground.th.domain.ImageFile;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.repository.MemberCustomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
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

    public Member login(MemberDto memberDto) {
        try {
            Member member = memberRepository.findByEmail(memberDto.getEmail()).get();
            if (passwordEncoder.matches(memberDto.getPassword(), member.getPassword()))
                return member;
        }catch(NoSuchElementException e) {
            return null;
        }
        return null;
    }


    public Set<Team> findAllRooms(String token) {

        Member member = memberRepository.findByEmail(token).get();

        return member.getGroups();

    }

    public ResponseMyProfileDto findByEmailToProfile(String userEmail) {
        Member member = memberRepository.findByEmail(userEmail).get();
        Set<Team> myTeams = member.getMyTeams();
        List<ImageFile> images = member.getImages();
        return new ResponseMyProfileDto(member,myTeams,images);
    }
}
