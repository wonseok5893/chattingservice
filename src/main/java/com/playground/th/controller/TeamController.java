package com.playground.th.controller;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.controller.dto.responseDto.ResponseData;
import com.playground.th.controller.dto.responseDto.ResponseFindRoomDto;
import com.playground.th.controller.dto.responseDto.ResponseTeam;
import com.playground.th.controller.dto.responseDto.ResponseTeamDto;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Team;
import com.playground.th.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeamController {
    private final TeamService teamService;

    // 소모임 생성 -> 채팅방 생성
    @PostMapping("/add/team")
    public ResponseTeamDto createTeam(@RequestBody TeamCreateForm teamDto) {
        return teamService.createTeam(teamDto);
    }

    //모든 소모임 검색
    @GetMapping("/all/teams")
    public ResponseData<List<ResponseTeam>> allTeams(){
        List<ResponseTeam> allTeams = teamService.findAllTeams();
        Collections.reverse(allTeams);
        return new ResponseData<>(1,allTeams);
    }

    @GetMapping("team/{teamId}")
    public ResponseFindRoomDto findRoom(@PathVariable("teamId")Long teamId){
        return teamService.findRoom(teamId);
    }


}
