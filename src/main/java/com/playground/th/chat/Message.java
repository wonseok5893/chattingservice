package com.playground.th.chat;


import lombok.Data;

@Data
public class Message {
    private String type;
    private String from; //user token <- string user_id 로 사용
    private String to; //
    private String text;
}
