package com.playground.th.controller.dto;

import com.playground.th.domain.ImageFile;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Data
public class ResponseMyProfileDto {
    Member member;
    Set<Team> teams;
    Set<String> images;
    public ResponseMyProfileDto(Member member, Set<Team> teams, List<ImageFile> imageFiles){
        member = member;
        teams = teams;
        imageFiles.forEach((e)-> images.add(e.getFileUrl()));
    }
}
