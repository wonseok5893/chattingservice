package com.playground.th.controller;

import com.playground.th.controller.dto.ChatRoomDto;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Team;
import com.playground.th.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;


    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatService.findByRoomId(roomId);
    }


}
