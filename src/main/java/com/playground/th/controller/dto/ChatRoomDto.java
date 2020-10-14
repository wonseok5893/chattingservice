package com.playground.th.controller.dto;

import com.playground.th.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter
public class ChatRoomDto {
    private int result;
    private List<ChatRoom> data;

    public ChatRoomDto(int result,List<ChatRoom> chatRooms) {
        this.result = result;
        this.data = chatRooms;
    }
}
