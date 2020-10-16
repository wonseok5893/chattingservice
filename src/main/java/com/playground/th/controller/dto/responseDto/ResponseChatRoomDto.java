package com.playground.th.controller.dto.responseDto;

import com.playground.th.domain.Team;

public class ResponseChatRoomDto {
    private String name;
    private String lastMemssage; // 파이널에 사용할거

    public ResponseChatRoomDto(Team team){
        name = team.getName();
        lastMemssage = "최근 메시지";
    }

}
