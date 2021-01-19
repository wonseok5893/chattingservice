package com.playground.th.controller.dto;

import com.playground.th.domain.Location;
import lombok.Data;

import javax.persistence.Embedded;

@Data
public class TeamCreateForm {
    private String email; //이번 프로젝트에서는 email로 사용
    private String name;
    private String content;
    private String location;
    private String category;
    private String startDate;
    private String endDate;
    private String teamImageUrl;
    private Integer maxMemberCount;
}
