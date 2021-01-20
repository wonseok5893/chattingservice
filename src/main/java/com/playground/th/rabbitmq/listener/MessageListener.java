package com.playground.th.rabbitmq.listener;

import com.playground.th.chat.Message;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.ChatRoomType;
import com.playground.th.domain.Member;
import com.playground.th.exception.TeamNotFoundException;
import com.playground.th.fcm.FCMMessage;
import com.playground.th.fcm.FCMService;
import com.playground.th.service.ChatService;
import com.playground.th.service.TeamService;
import com.playground.th.service.dto.findAllMembersQueryDto;
import com.playground.th.websocket.MyHandler;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
            // 소모임 참가 요청
            if (message.getType().equals("REQUEST") && message.getAria().equals("GROUP")) {
                String teamAdminEmail = teamService.findTeamAdminEmail(message.getTo());
                System.out.println(teamAdminEmail);
                myHandler.sendToMemberByEmail(teamAdminEmail, message);
                return;
            }
            // 방장 -> 소모임 참가 수락
            if (message.getType().equals("ACCEPT") && message.getAria().equals("GROUP")) {
                if (teamService.addMember(message.getFrom(), Long.valueOf(message.getTo()))) {
                    message.setType("ENTER");
                    sendByBroadCastByTeamId(message);
                }
                return;
            }
            //그룹 나누기 GROUP UNI
        }catch(Exception e){
            e.printStackTrace();
        }
        if(message.getType().equals("SEND")&&message.getAria().equals("GROUP"))
        sendByBroadCastByTeamId(message);

    }

    private void sendByBroadCastByTeamId(Message message) throws Exception {
        List<findAllMembersQueryDto> allMembers = teamService.findAllMembersEmail(message.getTo());
        //검증
        allMembers.parallelStream().forEach((member) -> {
            try {
                if(message.getText()!=null)
                fcmService.sendToMember(new FCMMessage(member.getToken(),"채팅 메시지가 왔습니다.",message.getText()));
                myHandler.sendToMemberByEmail(member.getEmail(), message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void sendByBroadCastByRoomId(Message message) throws Exception {
        Set<Member> allMembers = chatService.findAllMembers(message.getTo());
        // 회원 검증
        if (validateMember(allMembers, message.getFrom())) {
            // push 알림
            allMembers.parallelStream().forEach((member) -> {
                try {
                    myHandler.sendToMemberByEmail(member.getEmail(), message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            myHandler.sendToMemberByEmail(message.getFrom(), message);
        } else throw new RuntimeException("검증 되지 않은 회원이 보내는 채팅입니다.");
    }

    private boolean validateMember(Set<Member> allMembers, String from) {
        for (Member member : allMembers) {
            if (member.getEmail().equals(from)) return true;
        }
        return false;
    }


}
