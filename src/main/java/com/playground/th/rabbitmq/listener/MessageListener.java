package com.playground.th.rabbitmq.listener;

import com.playground.th.chat.Message;
import com.playground.th.domain.ChatRoom;
import com.playground.th.exception.TeamNotFoundException;
import com.playground.th.fcm.FCMMessage;
import com.playground.th.fcm.FCMService;
import com.playground.th.service.ChatService;
import com.playground.th.service.TeamService;
import com.playground.th.service.dto.findAllMembersQueryDto;
import com.playground.th.websocket.MyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageListener {
    private final ChatService chatService;
    private final TeamService teamService;
    private final MyHandler myHandler;
    private final FCMService fcmService;

    //Consumer
    @RabbitListener(queues = "message_queue")
    public void receiveMessage(Message message) throws Exception, TeamNotFoundException {
        log.info(message.parseToJson());

        try {
            //그룹 채팅
            if (message.getAria().equals("GROUP")) {
            // 소모임 참가 요청
                if (message.getType().equals("REQUEST") || message.getType().equals("ACCEPT")) {
                    //방장 아이디
                    Long teamAdminId = teamService.findTeamAdminId(message.getTo());
                    if (message.getType().equals("REQUEST")) {
                        myHandler.sendToMemberById(teamAdminId, message);
                        return;
                    }
                    // 방장 -> 소모임 참가 수락
                    if (message.getType().equals("ACCEPT")) {
                        //방장 검증 후
                        //## 생략
                        if (teamService.addMember(message.getFrom(), Long.valueOf(message.getTo()))) {
                            message.setType("ENTER");
                            sendByBroadCastByChatRoomId(message);
                        }
                        return;
                    }
                }
                if(message.getType().equals("SEND"))
                    sendByBroadCastByChatRoomId(message);
            }
            //1:1 채팅
            if(message.getAria().equals("PERSON")){
                if(message.getType().equals("ENTER")){
                    //1:1 채팅방 생성
                    ChatRoom chatRoom = chatService.createPersonChatRoom(message);
                    message.setTo(String.valueOf(chatRoom.getId()));
                }
                    sendByBroadCastByChatRoomId(message);
            }
            //그룹 나누기 GROUP UNI
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void sendByBroadCastByChatRoomId(Message message) throws Exception {
        List<findAllMembersQueryDto> allMembers = chatService.findAllMembersById(message.getTo());
        //검증

        allMembers.parallelStream().forEach((member) -> {
            try {
                if (message.getText() != null)
                    fcmService.sendToMember(new FCMMessage(member.getToken(), "채팅 메시지가 왔습니다.", message.getText()));
                myHandler.sendToMemberById(member.getId(), message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
