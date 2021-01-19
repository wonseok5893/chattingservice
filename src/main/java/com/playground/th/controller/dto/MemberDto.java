package com.playground.th.controller.dto;


import com.playground.th.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String email;
    private String password;
    private String nickname;
    private String sex;
    private Integer age;
    private String university;
    private String studentNumber;
    private String hobby;
    private String location;
    private String studentCardImageUrl;

}
