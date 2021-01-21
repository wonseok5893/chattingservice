package com.playground.th.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class findAllMembersQueryDto {
    private Long id;
    private String token;
}
