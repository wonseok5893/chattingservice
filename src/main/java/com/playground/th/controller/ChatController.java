package com.playground.th.controller;

import com.playground.th.chat.Message;
import com.playground.th.controller.dto.ChatMessageDto;
import com.playground.th.controller.dto.chat.ChatRoomMemberDTO;
import com.playground.th.controller.dto.responseDto.ResponseChatMessageDto;
import com.playground.th.controller.dto.responseDto.ResponseData;
import com.playground.th.controller.dto.responseDto.ResponseDto;
import com.playground.th.domain.ChatMessage;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.MessageType;
import com.playground.th.rabbitmq.Producer;
import com.playground.th.service.ChatService;
import com.playground.th.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;
    private final Producer producer;

    @GetMapping("/all/rooms")
    @ResponseBody
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    // 채팅메시지 -> rabbitmq
    // aria: GROUP, UNI type: REQEUST ACCEPT ENTER LEFT SEND
    @PostMapping("/chat/message")
    @ResponseBody
    public ResponseChatMessageDto sendChatMessage(@RequestHeader("Authorization") String tokenHeader, @RequestBody Message message) {
        // 사용자 변조 검증
        System.out.println(message);
        if (!message.getType().equals("ACCEPT")) {
            String token = JwtUtil.getTokenFromHeader(tokenHeader);
            Long id = JwtUtil.getUserIdFromToken(token);
            if (!id.equals(message.getFrom())) return new ResponseChatMessageDto(0, "잘못된 접근입니다");
        }
        producer.sendToExchange(message);
        return new ResponseChatMessageDto(1, "수신 확인");
    }
    @ResponseBody
    @PostMapping("/update/info/chatroom")
    public ResponseData<List<ChatRoomMemberDTO>> getUpdateChatRoomInfo(@RequestHeader("Authorization") String tokenHeader) {
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        Long id = JwtUtil.getUserIdFromToken(token);
        List<ChatRoomMemberDTO> updateChatRoomsInfo = chatService.findAllMyChatRooms(id);
        return new ResponseData<>(1, updateChatRoomsInfo);
    }


    @ResponseBody
    @PostMapping("/check/chatroom")
    public ResponseDto checkChatRoom(@RequestHeader("Authorization")String tokenHeader,@RequestBody Long otherId){
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        Long id = JwtUtil.getUserIdFromToken(token);
        Long chatRoomId = chatService.checkChatRoom(id, otherId);
        return chatRoomId==0?new ResponseDto(chatRoomId,"채팅방이 없습니다."):new ResponseDto(chatRoomId,"채팅방이 있어요");
    }

}
