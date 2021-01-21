package com.playground.th.controller.dto.chat;


import com.playground.th.domain.Member;
import lombok.Data;

@Data
public class ChatMemberDTO {
    private Long id;
    private String imageUrl;
    private String nickname;
    public ChatMemberDTO(Member member){
        id = member.getId();
        imageUrl = member.getImages().get(0).getFileUrl();
        nickname = member.getNickname();
    }
}
