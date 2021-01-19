package com.playground.th.exception;

public class ExistMemberInTeam extends RuntimeException {
    public ExistMemberInTeam(String email) {
        super(email+"is already existed in our team");
    }
}
