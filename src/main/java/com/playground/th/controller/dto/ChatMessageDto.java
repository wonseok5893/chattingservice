package com.playground.th.controller.dto;


import lombok.Data;


@Data
public class ChatMessageDto {
    private String type;
    private String roomId;
    private String sender;
    private String message;
}
