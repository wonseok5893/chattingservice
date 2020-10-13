package com.playground.th.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.th.controller.dto.ChatMessageDto;
import com.playground.th.domain.ChatMessage;
import com.playground.th.domain.ChatRoom;
import com.playground.th.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static java.awt.SystemColor.info;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
       // ChatMessageDto chatMessageDto = objectMapper.readValue(payload, ChatMessageDto.class);
       // log.info("chatmessage: {}", chatMessageDto.getMessage());
        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^");
        session.sendMessage(textMessage);
//        ChatRoom room = chatService.findById(chatMessageDto.getRoomId());
  //      room.handleActions(session, chatMessageDto, chatService);
    }
}
