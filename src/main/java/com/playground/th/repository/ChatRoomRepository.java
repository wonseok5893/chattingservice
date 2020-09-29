package com.playground.th.repository;

import com.playground.th.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    ChatRoom findByName(String name);
}
