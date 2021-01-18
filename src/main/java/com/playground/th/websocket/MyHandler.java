package com.playground.th.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.th.chat.Message;
import com.playground.th.domain.Member;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MyHandler extends TextWebSocketHandler {
    private ChatService chatService;
    private static List<WebSocketSession> users;
    private static Map<String,WebSocketSession> userMap;
    private ObjectMapper objectMapper = new ObjectMapper();

    protected MyHandler() {
        users = new ArrayList<>();
        userMap = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("hello new Session!!");
        users.add(session);
    }
    public void sendToMemberByEmail(String email, Message message) throws Exception {
        if(userMap.get(email)!=null) {
            System.out.println(message.getText());
            WebSocketSession webSocketSession = (WebSocketSession) userMap.get(email);
            TextMessage textMessage = new TextMessage(message.parseToJson());
            webSocketSession.sendMessage(textMessage);
        }
    }
    public void convertByHeader(Message message){
        switch(message.getType()){
            case "ENTER":{
                message.setText(message.getFrom()+"님이 입장하셨습니다,");
                break;
            }
            case "LEFT":{
                message.setText(message.getFrom()+"님이 나가셨습니다,");
            }
        }
    }
    public void sendToUser(Message message){

    }
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        Message message1 = objectMapper.readValue((String)message.getPayload(), Message.class);
        if(message1.getType().equals("CONNECT")){
            userMap.put(message1.getFrom(), session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("연결이 하나 끊어 졌습니다!.");
        users.remove(session);
    }
}
