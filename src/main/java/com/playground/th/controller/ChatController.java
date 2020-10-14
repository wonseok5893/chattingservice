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
<<<<<<< HEAD
import org.springframework.web.socket.TextMessage;
=======
>>>>>>> 3f740089c2cae5fb0e3b1c5de607d49d319774bc
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.server.PathParam;
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

    @MessageMapping("/chat/broad")
    @SendTo("/sub/public")
    public ChatMessageDto broadCast(ChatMessageDto chatMessageDto){
        System.out.println("send");
        return chatMessageDto;
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(chatMessageDto.getMessage());
        System.out.println(chatMessageDto.getRoomId());
        System.out.println(chatMessageDto.getSender());
        System.out.println(MessageType.ENTER);
        if (MessageType.ENTER.toString().equals(chatMessageDto.getType())) {
            chatMessageDto.setMessage(chatMessageDto.getSender()+" 님이 입장하셨습니다.");
        }
<<<<<<< HEAD
        headerAccessor.getSessionAttributes().put("sender",chatMessageDto.getSender());
        String text = "hello";
        TextMessage message = new TextMessage(text);
=======

        System.out.println("/sub/chat/room/"+chatMessageDto.getRoomId()+"로 "+chatMessageDto);
>>>>>>> 3f740089c2cae5fb0e3b1c5de607d49d319774bc
    }
}
