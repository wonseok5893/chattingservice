package com.playground.th.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class Producer implements ApplicationRunner {
    private final RabbitTemplate rabbitTemplate;
    private final ConnectionFactory connnectionFacyory;

    public void sendToExchange(String roomId){

        TopicExchange exchange = new TopicExchange("chat");
        rabbitTemplate.convertAndSend("chat","group."+roomId+".*","HELLO first");
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Queue queue = new Queue("group.room1.1",false);
        sendToExchange("room1");
    }
}
