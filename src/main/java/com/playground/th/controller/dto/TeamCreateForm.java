package com.playground.th.controller.dto;

import com.playground.th.domain.Location;
import lombok.Data;

import javax.persistence.Embedded;

@Data
public class TeamCreateForm {
    private String token; //이번 프로젝트에서는 email로 사용
    private String name;
    private String content;
    private String city;
    private String street;
    private String category;
    private int maxMemberCount;
}
