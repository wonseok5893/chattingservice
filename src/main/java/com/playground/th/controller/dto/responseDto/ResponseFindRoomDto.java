package com.playground.th.controller.dto.responseDto;

import com.playground.th.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseFindRoomDto {
    private String roomId;
    private Team team;

}
