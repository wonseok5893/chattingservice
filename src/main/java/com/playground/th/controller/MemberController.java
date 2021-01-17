package com.playground.th.controller;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.controller.dto.responseDto.ResponseChatRoomDto;
import com.playground.th.controller.dto.responseDto.ResponseDto;
import com.playground.th.domain.Member;
import com.playground.th.service.ImageFileService;
import com.playground.th.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    private final ImageFileService imageFileService;

    @PostMapping("/member/join")
    public ResponseDto join(@RequestPart("studentCard") MultipartFile file,@RequestPart("profile") List<MultipartFile> images,MemberDto memberDto) throws IOException {
        //회원가입 요청
        //학생증 사진 정보 임시 저장
        String studentCardImageUrl = imageFileService.getStudentCardImageUrl(memberDto.getEmail(),file.getOriginalFilename());
        memberDto.setStduentCardImageUrl(studentCardImageUrl);
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
        if(memberService.login(memberDto))
            return new ResponseDto(1,"로그인 성공");
        else
            return new ResponseDto(0,"로그인 실패");
    }

    @PostMapping("/my/chatRooms")
    public List<ResponseChatRoomDto> myChatRoom(@RequestBody String token){
        return memberService.findAllRooms(token);
    }
}
