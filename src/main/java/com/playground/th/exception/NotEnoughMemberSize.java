package com.playground.th.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotEnoughMemberSize extends RuntimeException {
    public NotEnoughMemberSize(String teamName) {
        super();
        log.error(teamName+"이 정원 초과로 멤버 추가가 불가능합니다");
    }
}
