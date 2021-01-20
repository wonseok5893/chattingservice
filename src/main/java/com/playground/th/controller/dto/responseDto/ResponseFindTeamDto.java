package com.playground.th.controller.dto.responseDto;

import com.playground.th.controller.dto.responseDto.team.ResponseFindTeamMemberDto;
import com.playground.th.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseFindTeamDto {
    private Long id;
    private String name;
    private String content;
    private String startDate;
    private String endDate;
    private List<ResponseFindTeamMemberDto> teamMembers;
    private int currentMemberSize;
    private String category;
    private String location;
    private String teamImageUrl;
    private Boolean admin;

    public ResponseFindTeamDto(Team team, List<ResponseFindTeamMemberDto> lists){
        id = team.getId();
        name = team.getName();
        content = team.getContent();
        startDate = team.getStartDate();
        endDate = team.getEndDate();
        currentMemberSize = team.getCurrentMemberCount();
        category = team.getCategory();
        location = team.getLocation();
        teamImageUrl = team.getTeamImageUrl();
        teamMembers = lists;
        admin = false;
    }
}
