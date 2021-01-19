package com.playground.th.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playground.th.controller.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;

    @JsonIgnore
    private String password;
    @Embedded
    private Student student;
    private String nickname;
    private String sex;
    private Integer age;
    private String hobby;
    private String location;
    @JsonIgnore
    private String token;

    private LocalDateTime registeredDate;

    @JsonIgnore
    @OneToMany(mappedBy = "teamAdmin")
    private Set<Team> myTeams = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "members")
    private Set<Team> groups = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "joinMembers")
    private Set<ChatRoom> chatRooms = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<ImageFile> images = new ArrayList<>();

    private String studentCardImageUrl;

    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @PrePersist
    public void createdAt() {
        this.registeredDate = LocalDateTime.now();
    }

    //생성메서드
    public static Member create(MemberDto memberDto) {
        Member member = new Member();
        member.setPassword(memberDto.getPassword());
        member.setEmail(memberDto.getEmail());
        member.setAge(memberDto.getAge());
        member.setSex(memberDto.getSex());
        member.setHobby(memberDto.getHobby());
        member.setNickname(memberDto.getNickname());
        member.setLocation(memberDto.getLocation());
        Student student = new Student(memberDto.getUniversity(),memberDto.getStudentNumber());
        member.setStudent(student);
        member.setRole(UserRole.ROLE_USER);
        member.setStudentCardImageUrl(memberDto.getStudentCardImageUrl());
        return member;
    }
}
