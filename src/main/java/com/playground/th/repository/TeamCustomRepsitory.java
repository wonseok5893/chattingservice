package com.playground.th.repository;

import com.playground.th.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamCustomRepsitory {
    private EntityManager em;



}
