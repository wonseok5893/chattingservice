package com.playground.th.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String email;
    private String password;
    @Embedded
    private Student student;
    private String nickname;
    private String sex;
    private String token;
}
