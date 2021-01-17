package com.playground.th.security.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String email){
        super(email + " NotFoundException");
    }

}