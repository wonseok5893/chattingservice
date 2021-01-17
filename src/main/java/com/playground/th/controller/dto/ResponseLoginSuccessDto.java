package com.playground.th.controller.dto;

import com.playground.th.controller.dto.responseDto.ResponseDto;
import com.playground.th.domain.Member;
import com.playground.th.utils.JwtUtil;
import lombok.Getter;


@Getter
public class ResponseLoginSuccessDto extends ResponseDto {
    private String token ;
    public ResponseLoginSuccessDto(Member member) {
        super(1,"로그인 성공");
        this.token = JwtUtil.generateJwtToken(member);
    }
}
