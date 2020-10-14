package com.playground.th.repository;

import com.playground.th.domain.ChatSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ChatSessionCustomRepository {
    private final EntityManager em;

    public ChatSession findBySessionId(String sessionId){
//        em.createQuery("select ")
        return null;
    }

}
