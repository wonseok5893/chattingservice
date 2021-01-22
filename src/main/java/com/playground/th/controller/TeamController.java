package com.playground.th.controller;

import com.playground.th.controller.dto.TeamCreateForm;
import com.playground.th.controller.dto.responseDto.*;

import com.playground.th.domain.Member;
import com.playground.th.service.ImageFileService;
import com.playground.th.service.TeamService;
import com.playground.th.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeamController {
    private final TeamService teamService;
    private final ImageFileService imageFileService;

    // 소모임 생성 -> 채팅방 생성
    @PostMapping("/add/team")
    public ResponseDto createTeam(@RequestHeader("Authorization")String tokenHeader, @RequestPart("file") MultipartFile file, TeamCreateForm teamDto) throws IOException {
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        String email = JwtUtil.getUserEmailFromToken(token);
        teamDto.setEmail(email);
        if(!file.isEmpty()) {
            System.out.println(file.getOriginalFilename());
            teamDto.setTeamImageUrl("/upload/image/"+file.getOriginalFilename());
            imageFileService.saveTeamImage(file);
        }
        return teamService.createTeam(teamDto)?new ResponseDto(1L,"성공적으로 소모임그룹을 만들었습니다."): new ResponseDto(0L,"실패");
    }

    //모든 소모임 검색
    @GetMapping("/all/teams")
    public ResponseData<List<ResponseTeam>> allTeams(){
        List<ResponseTeam> allTeams = teamService.findAllTeams();
        Collections.reverse(allTeams);
        return new ResponseData<>(1,allTeams);
    }

    @GetMapping("/team/{teamId}")
    public ResponseData<ResponseFindTeamDto> findTeamInfo(@RequestHeader("Authorization")String tokenHeader,@PathVariable("teamId")Long teamId){
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        String email = JwtUtil.getUserEmailFromToken(token);
        ResponseFindTeamDto teamInfo = teamService.findTeamInfo(teamId,email);
        if(teamInfo==null)return new ResponseData<>(0,null);
        return new ResponseData<>(1,teamInfo);
    }

    @PostMapping("/update/all/{teamId}")
    public ResponseDto updateTeamWithFile(@RequestHeader("Authorization")String tokenHeader, @RequestPart("file") MultipartFile file, TeamCreateForm teamDto,@PathVariable("teamId")Long teamId) throws IOException {
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        String email = JwtUtil.getUserEmailFromToken(token);
        teamDto.setEmail(email);

        return teamService.updateTeam(file, teamId,teamDto)?new ResponseDto(1L,"성공적으로 업데이트 하였습니다."): new ResponseDto(0L,"업데이트 실패");
    }
    @PostMapping("/update/info/{teamId}")
    public ResponseDto updateTeam(@RequestHeader("Authorization")String tokenHeader, TeamCreateForm teamDto,@PathVariable("teamId")Long teamId) throws IOException {
        String token = JwtUtil.getTokenFromHeader(tokenHeader);
        String email = JwtUtil.getUserEmailFromToken(token);
        teamDto.setEmail(email);

        return teamService.updateTeam(teamId,teamDto)?new ResponseDto(1L,"성공적으로 업데이트 하였습니다."): new ResponseDto(0L,"업데이트 실패");
    }

    @GetMapping("/team/search")
    public ResponseData<List<ResponseTeam>> getSearchTeams(@RequestParam("name") String name){

        return new ResponseData<>(1,teamService.findTeamsBySearch(name));
    }
}
