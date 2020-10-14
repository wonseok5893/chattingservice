package com.playground.th.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ChatSession {
    @Id @GeneratedValue
    Long id;
    String sessionId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chatroom_id")
    ChatRoom chatRoom;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    Member member;

    //생성메서드
    static ChatSession makeSession(String sessionId,Member member,ChatRoom chatRoom){
        ChatSession chatSession = new ChatSession();
        chatSession.setChatRoom(chatRoom);
        chatSession.setSessionId(sessionId);
        chatSession.setMember(member);
        return chatSession;
    }

}
