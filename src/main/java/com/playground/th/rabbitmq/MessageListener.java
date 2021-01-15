package com.playground.th.rabbitmq;

import com.playground.th.chat.Message;
import com.playground.th.domain.Member;
import com.playground.th.service.ChatService;
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
    private final MyHandler myHandler;
    //Consumer
    @RabbitListener(queues = "message_queue")
    public void receiveMessage(final Message message){
        //회원 검증
        Set<Member> allMembers = chatService.findAllMembers(message.getTo());
        // 그 채팅방에 있는 멤버가 맞는지 검토
        String validateId = validate(message, allMembers);
        if(!validateId.equals("none")){
            allMembers.parallelStream().forEach((member)-> {
                try {
                    myHandler.sendToRoom(member.getToken(),validateId,message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }else System.out.println("방에 없는 Id입니다");
        //

    }
    public String validate(Message message, Set<Member>members){
        for (Member member : members) {
            if(member.getToken().equals(message.getFrom())) return member.getNickname();
        }
        return "none";
    }


}
