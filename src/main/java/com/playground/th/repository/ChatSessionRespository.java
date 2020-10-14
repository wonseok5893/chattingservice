package com.playground.th.repository;

import com.playground.th.domain.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRespository extends JpaRepository<ChatSession, Long> {

}
