package com.playground.th.controller;

import com.playground.th.controller.dto.ChatMessageDto;
import com.playground.th.domain.ChatMessage;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.MessageType;
import com.playground.th.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/all/rooms")
    @ResponseBody
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }


}
