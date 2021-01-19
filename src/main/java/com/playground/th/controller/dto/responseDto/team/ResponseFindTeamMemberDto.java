package com.playground.th.controller.dto.responseDto.team;

import com.playground.th.domain.Member;
import lombok.Data;

@Data
public class ResponseFindTeamMemberDto {
    Long id;
    String imageUrl;
    String nickname;

    public ResponseFindTeamMemberDto(Member member){
       id = member.getId();
       imageUrl = member.getImages().get(0).getFileUrl();
       nickname = member.getNickname();
    }

}
