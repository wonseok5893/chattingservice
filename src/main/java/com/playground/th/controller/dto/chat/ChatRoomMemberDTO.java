package com.playground.th.controller.dto.chat;

import com.playground.th.domain.ChatRoom;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChatRoomMemberDTO {
    private String type;
    private String id; // group roomId // personal roomId
    private String name;
    private String teamImageUrl; // group 이면 있고
    private List<ChatMemberDTO> membersInfo;

    public ChatRoomMemberDTO(ChatRoom chatRoom) {
        type = String.valueOf(chatRoom.getType());
        id = String.valueOf(chatRoom.getId());
        name = chatRoom.getName();
        if(type.equals("GROUP"))
            this.teamImageUrl = chatRoom.getChatImageUrl();
        membersInfo = chatRoom.getJoinMembers().stream().map((member)->new ChatMemberDTO(member)).collect(Collectors.toList());
    }
}
