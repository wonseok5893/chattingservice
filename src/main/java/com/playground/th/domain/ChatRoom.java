package com.playground.th.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playground.th.chat.Message;
import com.playground.th.controller.dto.chat.ChatRoomCreateForm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
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

    private Integer currentMemberSize;
    private Integer maxMemberSize;

    private String chatImageUrl;
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
    public static ChatRoom groupCreate(String name, int maxMemberSize, Member member, String teamImageUrl) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(UUID.randomUUID().toString());
        chatRoom.setName(name);
        chatRoom.getJoinMembers().add(member);
        chatRoom.setCurrentMemberSize(1);
        chatRoom.setMaxMemberSize(maxMemberSize);
        chatRoom.setType(ChatRoomType.GROUP);
        chatRoom.setChatImageUrl(teamImageUrl);
        return chatRoom;
    }
    // uni request from id to id
    // 1대1 채팅 생성 메서드
    //public static ChatRoom uniCreate()

    public boolean addMember(Member member) {
        this.setCurrentMemberSize(this.getCurrentMemberSize()+1);
        this.getJoinMembers().add(member);
        return true;
    }
    // ENTER
    public static ChatRoom uniCreate(Member other,Member me){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(UUID.randomUUID().toString());
        chatRoom.setType(ChatRoomType.PERSON);
        chatRoom.addMember(other);
        chatRoom.addMember(me);
        chatRoom.setName(other.getNickname()+", "+me.getNickname());
        chatRoom.setMaxMemberSize(2);
        return chatRoom;
    }

}
