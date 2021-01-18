package com.playground.th.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
public class ChatRoom {

    @Id @GeneratedValue
    @Column(name="chatroom_id")
    private Long id;

    private String roomId;
    private String name;

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;

    @OneToOne(mappedBy = "chatRoom")
    private Team group;

    private int currentSize;

    @ManyToMany
    @JoinTable(name = "chatroom_member", joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> joinMembers = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdTime;

    @PrePersist
    public void createdAt() {
        this.createdTime = LocalDateTime.now();
    }

    //생성 메서드
    public static ChatRoom groupCreate(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(UUID.randomUUID().toString());
        chatRoom.setName(name);
        chatRoom.setType(ChatRoomType.GROUP);
        return chatRoom;
    }
    // uni request from id to id
    // 1대1 채팅 생성 메서드
    //public static ChatRoom uniCreate()



}
