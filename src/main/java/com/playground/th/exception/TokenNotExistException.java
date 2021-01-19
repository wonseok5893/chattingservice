package com.playground.th.exception;

public class TokenNotExistException extends RuntimeException {
    public TokenNotExistException(){
        super("token not be sent");
    }
}
