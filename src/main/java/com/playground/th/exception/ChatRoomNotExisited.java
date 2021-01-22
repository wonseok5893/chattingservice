package com.playground.th.exception;

public class ChatRoomNotExisited extends RuntimeException{
    public ChatRoomNotExisited(String to) {
        super(to+"is not existed");
    }
}
