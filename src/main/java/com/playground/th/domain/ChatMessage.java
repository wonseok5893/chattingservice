package com.playground.th.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ChatMessage {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;
    private String sender;
    private String message;
    private LocalDateTime sendTime;
}
