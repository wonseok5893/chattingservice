package com.playground.th.handler;

import com.playground.th.Stomp;
import com.playground.th.controller.dto.ChatMessageDto;
import com.playground.th.domain.ChatMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StompHandler {
    private Stomp stomp;
    private ChatMessageDto chatMessageDto;
    private WebSocketSession session;
    private static Map<String, Set<WebSocketSession>> sessionFactory = new HashMap<>();

    void sessionManage() {
        String roomId = chatMessageDto.getRoomId();
        switch (stomp.getCommand()) {
            case "SUBSCRIBE": {
                //채팅방 참가
                //세션검증
                //세션 추가
                chatMessageDto.setMessage("[ENTER] " + chatMessageDto.getSender()+"님이 입장하셨습니다");
                if (sessionFactory.get(roomId) == null) {
                    Set<WebSocketSession> sessions = new HashSet<>();
                    System.out.println("session size:" + sessions.size());
                    sessions.add(session);
                    sessionFactory.put(roomId, sessions);
                    System.out.println("session size:" + sessionFactory.get(roomId).size());
                } else sessionFactory.get(roomId).add(session);
                TextMessage broadMessage = new TextMessage(chatMessageDto.getMessage());
                broadCast(roomId, broadMessage);
                break;
            }
            case "UNSUBSCRIBE": {
                //채팅방 나가기
                chatMessageDto.setMessage("[LEAVE] "+chatMessageDto.getSender()+"님이 나가셨습니다");
                //세션 삭제
                if(sessionFactory.get(roomId)!=null&&sessionFactory.get(roomId).size()>=1){
                    sessionFactory.get(roomId).remove(session);
                }else if(sessionFactory.get(roomId).size()==0){
                    sessionFactory.remove(roomId);
                }
                TextMessage broadMessage = new TextMessage(chatMessageDto.getMessage());
                broadCast(roomId, broadMessage);
                break;
            }
            case "SEND":{
                TextMessage broadMessage = new TextMessage(chatMessageDto.getMessage());
                broadCast(roomId, broadMessage);
                break;
            }
        }
    }

    void broadCast(String roomId, TextMessage message) {
        if (sessionFactory.get(roomId) != null && sessionFactory.get(roomId).size() >= 1) {
            sessionFactory.get(roomId).parallelStream().forEach(o1 -> {
                try {
                    o1.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public StompHandler(Stomp stomp, ChatMessageDto chatMessageDto, WebSocketSession session) {
        this.stomp = stomp;
        this.chatMessageDto = chatMessageDto;
        this.session = session;
    }
}
