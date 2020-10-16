package com.playground.th.service;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.controller.dto.responseDto.ResponseFindRoomDto;
import com.playground.th.controller.dto.responseDto.ResponseTeam;
import com.playground.th.controller.dto.responseDto.ResponseTeamDto;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamCustomRepsitory;
import com.playground.th.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TeamService {
    private final TeamRepository teamRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final TeamCustomRepsitory teamCustomRepsitory;

    @Transactional
    public ResponseTeamDto createTeam(TeamCreateForm teamDto) {
        //멤버 조회
        Member member = memberRepository.findByEmail(teamDto.getToken());
        //팀 생성
        Team team = Team.createTeam(teamDto.getName(), teamDto.getContent()
                , teamDto.getCity(), teamDto.getStreet(), teamDto.getCategory()
        ,teamDto.getMaxMemberCount());
        //채팅방 생성
        ChatRoom chatRoom = ChatRoom.create(team.getName());
        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

        team.setChatRoom(chatRoom);
        team.getMembers().add(member);
        teamRepository.save(team);

        return new ResponseTeamDto(chatRoom.getRoomId(),teamDto);
    }

    public List<ResponseTeam> findAllTeams() {
        List<Team> all = teamRepository.findAll();
        List<ResponseTeam> responseTeams
                = all.stream().map(o1->new ResponseTeam(o1)).collect(Collectors.toList());
        return responseTeams;
    }


    public ResponseFindRoomDto findRoom(Long teamId) {
        Team team = teamRepository.findById(teamId).get();
        String roomId = team.getChatRoom().getRoomId();
        return new ResponseFindRoomDto(roomId,team);
    }
}
