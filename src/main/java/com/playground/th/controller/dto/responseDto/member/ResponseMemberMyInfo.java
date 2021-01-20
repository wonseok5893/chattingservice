package com.playground.th.controller.dto.responseDto.member;

import com.playground.th.domain.ImageFile;
import com.playground.th.domain.Member;
import com.playground.th.domain.Student;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResponseMemberMyInfo {
    private String name;
    private String location;
    private String university;
    private String introduction;
    private String hobby;
    private List<String> images;
    private List<ResponseMyTeamInfo> myteams;

    public ResponseMemberMyInfo(Member member, List<String>image, List<ResponseMyTeamInfo> teams){
        name = member.getNickname();
        location = member.getLocation();
        university = member.getStudent().getUniversity();
        introduction = member.getIntroduction();
        hobby = member.getHobby();
        images = image;
        myteams = teams;

    }
}

