package com.playground.th.chat;

import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;


class MessageTest {
    @Test
    void 파싱(){

        Message message = new Message("GROUP","ENTER","wonseok","hyun","안녕");
        System.out.println(message.parseToJson());
    }

}