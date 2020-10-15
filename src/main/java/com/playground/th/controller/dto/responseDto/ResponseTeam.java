package com.playground.th.controller.dto.responseDto;

import com.playground.th.domain.Location;
import com.playground.th.domain.Team;
import lombok.Data;

@Data
public class ResponseTeam {
    private String name;
    private String content;
    private int maxMemberCount;
    private Location location;
    private String category;

    public ResponseTeam(Team team){
        name = team.getName();
        content = team.getContent();
        maxMemberCount = team.getMaxMemberCount();
        location = team.getLocation();
        category = team.getCategory();
    }
}
