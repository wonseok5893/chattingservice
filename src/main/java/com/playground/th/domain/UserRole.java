package com.playground.th.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@Getter
public enum UserRole {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String value;

}
