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
    public ChatRoom finByRoomId(String roomId){
        return em.createQuery("select m from ChatRoom m" +
                        " where m.roomId = :roomId"
                ,ChatRoom.class)
                .setParameter("roomId",roomId).getSingleResult();
    }

}
