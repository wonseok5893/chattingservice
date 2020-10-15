package com.playground.th.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
public class Location {
    private String CIty;
    private String Street;
}
