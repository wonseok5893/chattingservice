package com.playground.th.controller;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.controller.dto.responseDto.ResponseTeamDto;
import com.playground.th.domain.ChatRoom;
import com.playground.th.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TeamController {
    private final TeamService teamService;

    // 채팅방 생성
    @PostMapping("/add/team")
    public ResponseTeamDto createTeam(@RequestBody TeamCreateForm teamDto) {
        return teamService.createTeam(teamDto);
    }

}
