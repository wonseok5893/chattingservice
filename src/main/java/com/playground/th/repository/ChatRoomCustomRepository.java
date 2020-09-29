package com.playground.th.repository;

import com.playground.th.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ChatRoomCustomRepository {
    private final EntityManager em;


    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        return chatRoom;
    }
}
