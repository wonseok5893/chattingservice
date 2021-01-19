package com.playground.th.controller;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.controller.dto.ResponseLoginSuccessDto;
import com.playground.th.controller.dto.responseDto.ResponseDto;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.service.ImageFileService;
import com.playground.th.service.MemberService;
import com.playground.th.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    private final ImageFileService imageFileService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/member/join")
    public ResponseDto join(@RequestPart("studentCard") MultipartFile file,@RequestPart("profile") List<MultipartFile> images,MemberDto memberDto) throws IOException {
        //회원가입 요청
        //학생증 사진 정보 임시 저장
        String studentCardImageUrl = imageFileService.getStudentCardImageUrl(memberDto.getEmail(),file.getOriginalFilename());
        memberDto.setStudentCardImageUrl(studentCardImageUrl);
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member newMember = memberService.join(memberDto);
        //실패시
        if(newMember==null)return new ResponseDto(0,"회원가입 실패");
        //성공시 학생증 보관
        imageFileService.saveStudentCard(newMember,file);
        // 사진 있으면 보관
        for(MultipartFile image: images)
        imageFileService.saveImageFile(image,newMember);

        return new ResponseDto(1,"성공적으로 회원가입 하였습니다.");
    }
    @PostMapping("/member/login")
    public ResponseDto login(@RequestBody MemberDto memberDto) {
        Member member = memberService.login(memberDto);
        if(member!=null) {
            return new ResponseLoginSuccessDto(member);
        }
        else
            return new ResponseDto(0,"로그인 실패");
    }

    @PostMapping("/my/teams")
    public Set<Team> myChatRoom(@RequestHeader("Authorization")String tokenHeader){
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        String userEmail = JwtUtil.getUserEmailFromToken(token);
        return memberService.findAllMyTeams(userEmail);
    }

    @PostMapping("/my/profile")
    public Member myProfile(@RequestHeader("Authorization")String tokenHeader){
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        String userEmail = JwtUtil.getUserEmailFromToken(token);
        return memberService.findByEmailToProfile(userEmail);
    }
}
