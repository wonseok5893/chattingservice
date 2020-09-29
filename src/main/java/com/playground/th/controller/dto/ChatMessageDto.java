package com.playground.th.controller.dto;

import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.MessageType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class ChatMessageDto {
    private String type;
    private String roomId;
    private String sender;
    private String message;
}
