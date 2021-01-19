package com.playground.th.controller.dto.responseDto;

import com.playground.th.domain.Location;
import com.playground.th.domain.Team;
import lombok.Data;

@Data
public class ResponseTeam {
    private Long teamId;
    private String name;
    private String content;
    private int currentMemberCount;
    private int maxMemberCount;
    private String location;
    private String category;
    private String teamImageUrl;
    public ResponseTeam(Team team){
        teamId = team.getId();
        name = team.getName();
        content = team.getContent();
        currentMemberCount = team.getCurrentMemberCount();
        maxMemberCount = team.getMaxMemberCount();
        location = team.getLocation();
        category = team.getCategory();
        teamImageUrl = team.getTeamImageUrl();
    }
}


