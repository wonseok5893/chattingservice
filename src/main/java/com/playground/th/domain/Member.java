package com.playground.th.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @JsonIgnore
    @ManyToMany(mappedBy = "members")
    private Set<Team> groups = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<ImageFile> images = new ArrayList<>();

    //생성메서드
    public static Member create(String email, String password
    ) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        return member;
    }
}
