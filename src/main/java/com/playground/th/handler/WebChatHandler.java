package com.playground.th.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.th.Stomp;
import com.playground.th.controller.dto.ChatMessageDto;
import com.playground.th.domain.ChatMessage;
import com.playground.th.domain.ChatRoom;
import com.playground.th.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebChatHandler extends TextWebSocketHandler {
    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    private static Map<String, Set<WebSocketSession>> sessionFactory = new HashMap<>();

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        Stomp stomp = new Stomp(payload);
        if (!stomp.getCommand().equals("DISCONNECT")){
            ChatMessageDto chatMessageDto = objectMapper.readValue(stomp.getBody(), ChatMessageDto.class);
            StompHandler stompHandler = new StompHandler(stomp, chatMessageDto, session);
            stompHandler.sessionManage();
        }else{
            /*
            case "DISCONNECT": {
                //검증
                //세션 삭제
                break;
            }
            */
        }

    }
}
