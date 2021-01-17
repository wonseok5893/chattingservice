package com.playground.th.service;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.controller.dto.responseDto.ResponseChatRoomDto;
import com.playground.th.controller.dto.responseDto.ResponseTeam;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

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
        Member existMember = memberRepository.findByEmail(email);
        if (existMember != null) {
            return false;
        }
        return true;
    }

    public boolean login(MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.getEmail());
        if (memberDto.getPassword().equals(member.getPassword()))
            return true;
        return false;
    }


    public List<ResponseChatRoomDto> findAllRooms(String token) {
        Member member = memberRepository.findByEmail(token);
        
        if (member.getGroups() != null) {
            Set<Team> groups = member.getGroups();
            return groups.stream().map(o1 -> new ResponseChatRoomDto(o1)).collect(Collectors.toList());
        }
        return new ArrayList<>();

    }
}
