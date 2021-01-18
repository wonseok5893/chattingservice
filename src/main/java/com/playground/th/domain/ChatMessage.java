package com.playground.th.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chatmessage_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;
    private String sender;
    private String message;
    @CreatedDate
    private LocalDateTime sendTime;
}
