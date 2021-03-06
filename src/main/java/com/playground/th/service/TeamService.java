package com.playground.th.service;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.controller.dto.responseDto.ResponseData;
import com.playground.th.controller.dto.responseDto.ResponseFindTeamDto;
import com.playground.th.controller.dto.responseDto.ResponseTeam;
import com.playground.th.controller.dto.responseDto.team.ResponseFindTeamMemberDto;

import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.exception.NotAllowedOverCurrentMemberSize;
import com.playground.th.exception.NotFoundFileException;
import com.playground.th.exception.TeamNotFoundException;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamCustomRepsitory;
import com.playground.th.repository.TeamRepository;
import com.playground.th.security.exception.UserNotFoundException;
import com.playground.th.service.dto.findAllMembersQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
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
    private final ImageFileService imageFileService;

    @Transactional
    public boolean createTeam(TeamCreateForm teamDto) {
        //멤버 조회
        try {
            Member member = memberRepository.findByEmail(teamDto.getEmail()).orElseThrow(() -> new UserNotFoundException(teamDto.getEmail()));
            //채팅방 생성
            ChatRoom chatRoom = ChatRoom.groupCreate(teamDto.getName(), Integer.parseInt(teamDto.getMaxMemberSize()), member,teamDto.getTeamImageUrl());
            //팀 생성
            Team team = Team.createTeam(teamDto.getName(), teamDto.getContent()
                    , teamDto.getLocation(), teamDto.getCategory()
                    , Integer.parseInt(teamDto.getMaxMemberSize()), teamDto.getTeamImageUrl(), teamDto.getStartDate(), teamDto.getEndDate(), member, chatRoom);
            //검증

            //채팅방 생성
            ChatRoom savedRoom = chatRoomRepository.save(chatRoom);

            team.setChatRoom(chatRoom);
            team.getMembers().add(member);
            teamRepository.save(team);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<ResponseTeam> findAllTeams() {
        List<Team> all = teamRepository.findAll();
        List<ResponseTeam> responseTeams
                = all.stream().map(o1->new ResponseTeam(o1)).collect(Collectors.toList());
        return responseTeams;
    }


    public ResponseFindTeamDto findTeamInfo(Long teamId,String email) {
        try {
            Team team = teamRepository.findById(teamId).orElseThrow(()->new TeamNotFoundException(teamId));
            Set<Member> members = team.getMembers();
            List<ResponseFindTeamMemberDto> memberLists = members.stream().map((member) -> new ResponseFindTeamMemberDto(member)).collect(Collectors.toList());
            Member teamAdmin = team.getTeamAdmin();
            ResponseFindTeamDto responseFindTeamDto = new ResponseFindTeamDto(team,teamAdmin, memberLists);
            if(teamAdmin.getEmail().equals(email)) responseFindTeamDto.setAdmin(true);
            // 방장 검증
            return responseFindTeamDto;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Long addMember(Long id,Long teamId) throws Exception {
        try {
            Member member = memberRepository.findById(id).get();
            System.out.println(member);
            if (member == null) throw new UserNotFoundException(id + "인 사용자가 없습니다");
            Team team = teamRepository.findById(teamId).get();
            team.addMember(member);
            team.getChatRoom().addMember(member);
            return team.getChatRoom().getId();
        }catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }

    public Long findTeamAdminId(String to) throws TeamNotFoundException {
        Team team = teamRepository.findById(Long.valueOf(to)).get();
        if(team==null) throw new TeamNotFoundException(to);
        return team.getTeamAdmin().getId();
    }





    @Transactional
    public boolean updateTeam(MultipartFile file, Long teamId, TeamCreateForm teamDto) {
        try{
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));
        if(!team.getTeamAdmin().getEmail().equals(teamDto.getEmail())) throw new AuthenticationException("방장이 아닌 잘못된 접근");
            if(team.getCurrentMemberCount()>Integer.parseInt(teamDto.getMaxMemberSize())) throw new NotAllowedOverCurrentMemberSize();
            //이미지
            if(file!=null) {
                String[] split = team.getTeamImageUrl().split("/");
                String existFileName = split[split.length - 1];
                System.out.println(existFileName+" 이거 ");
                if (!file.getOriginalFilename().equals(existFileName)) {
                    if(!imageFileService.deleteTeamImage(existFileName))throw new NotFoundFileException(existFileName);
                    team.setTeamImageUrl("/upload/image/" + file.getOriginalFilename());
                    imageFileService.saveTeamImage(file);
                }
            }
            team.setName(teamDto.getName());
            team.setContent(teamDto.getContent());
            team.setLocation(teamDto.getLocation());
            team.setCategory(teamDto.getCategory());
            team.setMaxMemberCount(Integer.parseInt(teamDto.getMaxMemberSize()));
            team.setStartDate(teamDto.getStartDate());
            team.setEndDate(teamDto.getEndDate());
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean updateTeam(Long teamId, TeamCreateForm teamDto) {
        try{
            Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));
            if(!team.getTeamAdmin().getEmail().equals(teamDto.getEmail())) throw new AuthenticationException("방장이 아닌 잘못된 접근");
            if(team.getCurrentMemberCount()>Integer.parseInt(teamDto.getMaxMemberSize())) throw new NotAllowedOverCurrentMemberSize();
            team.setName(teamDto.getName());
            team.setContent(teamDto.getContent());
            team.setLocation(teamDto.getLocation());
            team.setCategory(teamDto.getCategory());
            team.setMaxMemberCount(Integer.parseInt(teamDto.getMaxMemberSize()));
            team.setStartDate(teamDto.getStartDate());
            team.setEndDate(teamDto.getEndDate());
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<ResponseTeam> findTeamsBySearch(String name) {
        return teamRepository.findByNameContains(name).stream().map((team)->new ResponseTeam(team)).collect(Collectors.toList());
    }

    public Long findChatRoomId(String to) {
        Team team = teamRepository.findById(Long.valueOf(to)).orElseThrow(() -> new TeamNotFoundException(to));
        return team.getChatRoom().getId();
    }
}
