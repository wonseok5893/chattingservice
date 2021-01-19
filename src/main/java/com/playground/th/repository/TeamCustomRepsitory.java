package com.playground.th.repository;

import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamCustomRepsitory {
    private EntityManager em;


    public Team findByIdToMember(String to) {
        return em.createQuery("select t from Team t" +
                " join fetch t.teamAdmin m"
                , Team.class
        ).getSingleResult();
    }
}
