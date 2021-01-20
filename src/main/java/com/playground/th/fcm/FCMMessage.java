package com.playground.th.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FCMMessage {
    private FCMMessageData data;
    private String to;
    public FCMMessage(String token,String title,String message){
        to = token;
        data = new FCMMessageData(title, message);
    }
    @AllArgsConstructor
    @Data
    private class FCMMessageData {
        private String title;
        private String message;
    }
}