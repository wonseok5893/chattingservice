package com.playground.th.service;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.controller.dto.responseDto.ResponseTeamDto;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TeamService {
    private final TeamRepository teamRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseTeamDto createTeam(TeamCreateForm teamDto) {
        Member member = memberRepository.findByEmail(teamDto.getToken());
        Team team = Team.createTeam(teamDto.getName(), teamDto.getDescription()
                , teamDto.getCity(), teamDto.getStreet(), teamDto.getCategory()
        ,teamDto.getMaxMemberCount());

        ChatRoom chatRoom = ChatRoom.create(team.getName());
        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

        team.setChatRoom(chatRoom);
        team.getMembers().add(member);
        teamRepository.save(team);

        return new ResponseTeamDto(chatRoom.getRoomId(),teamDto);
    }
}
