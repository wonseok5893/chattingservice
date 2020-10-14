package com.playground.th;


import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class Stomp {
    private String command;
    private Map<String,String> headers;
    private String body;

    public Stomp(String command, Map<String,String> headers, String body) {
        this.command = command;
        this.headers = headers;
        this.body = body;
    }
    public Stomp(String payload){
        String[] splitArr = payload.split("\n\n");
        String[] headers = splitArr[0].split("\n");
        Map<String,String> header = new HashMap<>();
        for (int i = 1; i < headers.length; i++) {
            String key = headers[i].split(":")[0];
            String value = headers[i].split(":")[1];
            header.put(key,value);
        }
        this.command = headers[0];
        this.headers = header;
        this.body = splitArr[1];
    }

}
