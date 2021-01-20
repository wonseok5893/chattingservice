package com.playground.th.exception;

public class NotAllowedOverCurrentMemberSize extends RuntimeException {
    public NotAllowedOverCurrentMemberSize() {
        super("cant change below currentMembersize to Maxsize");
    }
}
