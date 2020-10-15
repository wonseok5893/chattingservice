package com.playground.th.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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
    private String password;
    @Embedded
    private Student student;
    private String nickname;
    private String sex;
    private String token;
    @ManyToMany(mappedBy = "members")
    private Set<Team> groups = new HashSet<>();

    //생성메서드
    public static Member create(String email, String password
    ) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        return member;
    }
}
