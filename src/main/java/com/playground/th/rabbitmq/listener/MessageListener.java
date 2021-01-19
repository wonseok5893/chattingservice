package com.playground.th.rabbitmq.listener;

import com.playground.th.chat.Message;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.ChatRoomType;
import com.playground.th.domain.Member;
import com.playground.th.service.ChatService;
import com.playground.th.service.TeamService;
import com.playground.th.websocket.MyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class MessageListener {
    private final ChatService chatService;
    private final TeamService teamService;
    private final MyHandler myHandler;

    //Consumer
    @RabbitListener(queues = "message_queue")
    public void receiveMessage(Message message) throws Exception {

        // 소모임 참가 요청
        if (message.getType().equals("REQUEST") && message.getAria().equals("GROUP")) {
            String teamAdminEmail = teamService.findTeamAdmin(message.getTo()).getEmail();
            System.out.println(teamAdminEmail);
            myHandler.sendToMemberByEmail(teamAdminEmail, message);
            return;
        }
        // 방장 -> 소모임 참가 수락
        if (message.getType().equals("ACCEPT") && message.getAria().equals("GROUP")) {
            if (teamService.addMember(message.getFrom(), Long.valueOf(message.getTo()))) {
                message.setType("ENTER");
                sendByBroadCast(message);
            }
            return;
        }
            //그룹 나누기 GROUP UNI
            sendByBroadCast(message);



    }

    private void sendByBroadCast(Message message) throws Exception {
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