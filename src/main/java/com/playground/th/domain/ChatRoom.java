package com.playground.th.domain;


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


    //생성 메서드
    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId("1");
        chatRoom.setName(name);
        return chatRoom;
    }



}
