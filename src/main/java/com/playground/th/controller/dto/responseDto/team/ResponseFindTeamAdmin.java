package com.playground.th.controller.dto.responseDto.team;

import com.playground.th.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseFindTeamAdmin {
    private Long id;

    public ResponseFindTeamAdmin(Member member) {
        id= member.getId();
    }
}
