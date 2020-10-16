package com.playground.th.repository;

import com.playground.th.domain.Team;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class MemberCustomRepository {
    private final EntityManager em;
}
