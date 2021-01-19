package com.playground.th.exception;

public class TeamNotFoundException extends Throwable {
    public TeamNotFoundException(String to) {
        System.out.println(to+"인 팀이 존재하지 않습니다");
    }
}
