package com.playground.th.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.th.chat.Message;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHandler extends TextWebSocketHandler {
//    private final ChatService chatService;
    private List<WebSocketSession> users;
    private Map<String, Object> userMap;
    private ObjectMapper objectMapper = new ObjectMapper();

    public MyHandler() {
        users = new ArrayList<>();
        userMap = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("hello new Session!!");
        users.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Message message1 = objectMapper.readValue((String) message.getPayload(), Message.class);

        //메시지 파싱
        if (message1.getType().equals("ENTER")) {
            userMap.put(message1.getFrom(), session);
            // 채팅방 조회 멤버들
            // id -> session
            ArrayList<WebSocketSession> lists = new ArrayList<>();
            TextMessage textMessage = new TextMessage(message1.getFrom()+"님이 입장하셨습니다");
//            lists.parallelStream().forEach((se)-> {
//                try {
//                    se.sendMessage(textMessage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            userMap.get(message.ge)
            session.sendMessage(textMessage);

        } else if (message1.getType().equals("DUT")) {

        } else if (message1.getType().equals("SEND")) {
            WebSocketSession ws = (WebSocketSession) userMap.get(message1.getTo());
            TextMessage textMessage = new TextMessage(message1.getText());
            ws.sendMessage(textMessage);
        }
        // id 검증
        // 채팅방 사람들에게 모두 채팅메시지 보냄


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("연결이 하나 끊어 졌습니다!.");
        users.remove(session);
    }
}
