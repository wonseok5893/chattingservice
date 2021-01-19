package com.playground.th.repository;

import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepository  {
    private final EntityManager em;

}
