package com.playground.th.controller.dto.responseDto;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTeamDto {
    private int result;
    private String message;
}
