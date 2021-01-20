package com.playground.th.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String to) {
        System.out.println(to+"인 팀이 존재하지 않습니다");
    }

    public TeamNotFoundException(Long teamId) {
        System.out.println(teamId+"인 팀이 존재하지 않습니다");
    }
}
