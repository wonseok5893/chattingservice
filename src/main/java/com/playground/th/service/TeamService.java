package com.playground.th.service;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.controller.dto.responseDto.ResponseData;
import com.playground.th.controller.dto.responseDto.ResponseFindTeamDto;
import com.playground.th.controller.dto.responseDto.ResponseTeam;
import com.playground.th.controller.dto.responseDto.team.ResponseFindTeamMemberDto;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.exception.TeamNotFoundException;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamCustomRepsitory;
import com.playground.th.repository.TeamRepository;
import com.playground.th.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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
    public boolean createTeam(TeamCreateForm teamDto) {
        //멤버 조회
        try {
            Member member = memberRepository.findByEmail(teamDto.getEmail()).orElseThrow(() -> new UserNotFoundException(teamDto.getEmail()));
            //채팅방 생성
            ChatRoom chatRoom = ChatRoom.groupCreate(teamDto.getName(), teamDto.getMaxMemberCount(), member);
            //팀 생성
            Team team = Team.createTeam(teamDto.getName(), teamDto.getContent()
                    , teamDto.getLocation(), teamDto.getCategory()
                    , teamDto.getMaxMemberCount(), teamDto.getTeamImageUrl(), teamDto.getStartDate(), teamDto.getEndDate(), member, chatRoom);
            //검증

            //채팅방 생성
            ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

            team.setChatRoom(chatRoom);
            team.getMembers().add(member);
            teamRepository.save(team);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public List<ResponseTeam> findAllTeams() {
        List<Team> all = teamRepository.findAll();
        List<ResponseTeam> responseTeams
                = all.stream().map(o1->new ResponseTeam(o1)).collect(Collectors.toList());
        return responseTeams;
    }


    public ResponseFindTeamDto findTeamInfo(Long teamId) {
        try {
            Team team = teamRepository.findById(teamId).get();
            if (team==null)throw new TeamNotFoundException(String.valueOf(teamId));
            Set<Member> members = team.getMembers();
            List<ResponseFindTeamMemberDto> memberLists = members.stream().map((member) -> new ResponseFindTeamMemberDto(member)).collect(Collectors.toList());
            return new ResponseFindTeamDto(team,memberLists);
        }catch(Exception | TeamNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public boolean addMember(String email,Long teamId) throws Exception {
        try {
            Member member = memberRepository.findByEmail(email).get();
            if (member == null) throw new UserNotFoundException(email + "인 사용자가 없습니다");
            Team team = teamRepository.findById(teamId).get();
            team.addMember(member);
            team.getChatRoom().addMember(member);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String findTeamAdminEmail(String to) throws TeamNotFoundException {
        Team team = teamRepository.findById(Long.valueOf(to)).get();
        if(team==null) throw new TeamNotFoundException(to);
        String email = team.getTeamAdmin().getEmail();
        return email;
    }

    public List<String> findAllMembers(String to) {
        Team team = teamRepository.findById(Long.valueOf(to)).get();
        return team.getMembers().stream().map((member) -> member.getEmail()).collect(Collectors.toList());


    }
}
