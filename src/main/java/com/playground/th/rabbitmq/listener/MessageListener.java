package com.playground.th.rabbitmq.listener;

import com.playground.th.chat.Message;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.ChatRoomType;
import com.playground.th.domain.Member;
import com.playground.th.exception.TeamNotFoundException;
import com.playground.th.service.ChatService;
import com.playground.th.service.TeamService;
import com.playground.th.websocket.MyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageListener {
    private final ChatService chatService;
    private final TeamService teamService;
    private final MyHandler myHandler;

    //Consumer
    @RabbitListener(queues = "message_queue")
    public void receiveMessage(Message message) throws Exception, TeamNotFoundException {

        log.info(message.parseToJson());
        /*
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
            sendByBroadCastByTeamId(message);
        }catch(Exception e){
            e.printStackTrace();
        }
        */
        sendByBroadCastByTeamId(message);
    }

    private void sendByBroadCastByTeamId(Message message) throws Exception {
        List<String> allMembers = teamService.findAllMembers(message.getTo());
        //검증
        allMembers.parallelStream().forEach((member) -> {
            try {
                myHandler.sendToMemberByEmail(member, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        myHandler.sendToMemberByEmail(message.getFrom(), message);
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
