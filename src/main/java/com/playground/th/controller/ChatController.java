package com.playground.th.controller;

import com.playground.th.controller.dto.ChatMessageDto;
import com.playground.th.domain.ChatMessage;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.MessageType;
import com.playground.th.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations messageTemplate;

    @GetMapping("/all/rooms")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto chatMessageDto) {

        if (MessageType.ENTER.equals(chatMessageDto.getType())) {
            chatMessageDto.setMessage(chatMessageDto.getSender()+" 님이 입장하셨습니다.");
        }
        messageTemplate.convertAndSend("/sub/chat/room/"+chatMessageDto.getRoomId());
    }
}
