package com.playground.th.controller;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.controller.dto.ResponseDto;
import com.playground.th.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/join")
    public ResponseDto join(@RequestBody MemberDto memberDto) {
        if(memberService.join(memberDto))
            return new ResponseDto(1,"회원가입 성공");
        else
            return new ResponseDto(0,"회원가입 실패");
    }
    @PostMapping("/member/login")
    public ResponseDto login(@RequestBody MemberDto memberDto) {
        if(memberService.login(memberDto))
            return new ResponseDto(1,"로그인 성공");
        else
            return new ResponseDto(0,"로그인 실패");
    }
}
