package com.playground.th.chat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String aria;
    private String type;
    private Long from; //user token <- string user_id 로 사용
    private String to; //
    private String text;
    private Long sendTime;

    public String parseToJson() {
        return "{\"aria\":\""+aria+"\",\"type\":\""+type+"\", \"from\":\""+from+"\", \"to\":\""+to+"\", \"text\":\""+text+"\" , \"sendTime\":\""+sendTime+"\"}";
    }
}
