package com.playground.th.controller.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class ResponseChatMessageDto {
    int result;
    String message;
}
