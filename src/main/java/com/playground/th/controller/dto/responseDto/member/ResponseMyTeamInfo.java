package com.playground.th.controller.dto.responseDto.member;

import com.playground.th.domain.Team;
import lombok.Data;

@Data
public class ResponseMyTeamInfo {

    private Long id;
    private String teamImageUrl;
    private String name;
    private String location;
    private String category;
    private int maxMemberSize;
    private int currentMemberSize;

    public ResponseMyTeamInfo(Team team){
        id = team.getId();
        teamImageUrl = team.getTeamImageUrl();
        name = team.getName();
        category = team.getCategory();
        maxMemberSize = team.getMaxMemberCount();
        location = team.getLocation();
        currentMemberSize = team.getCurrentMemberCount();
    }

}
