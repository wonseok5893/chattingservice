package com.playground.th.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playground.th.controller.dto.ChatMessageDto;
import com.playground.th.service.ChatService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
public class ChatRoom {
    @Id
    private String roomId;
    private String name;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages;

    @JsonIgnore
    @Transient
    private Set<WebSocketSession> sessions = new HashSet<>();

    //생성 메서드
    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId("1");
        chatRoom.setName(name);
        return chatRoom;
    }

    public void handleActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
        if (chatMessageDto.getType().equals(MessageType.ENTER.toString())) {
            sessions.add(session);
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessageDto, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }



}
