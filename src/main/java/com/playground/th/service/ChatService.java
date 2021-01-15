package com.playground.th.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.repository.ChatRoomCustomRepository;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomCustomRepository chatRoomCustomRepository;

    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> allRooms = chatRoomRepository.findAll();
        Collections.reverse(allRooms);
        return allRooms;
    }

    public ChatRoom findById(String id) {
        return chatRoomRepository.findById(id).get();
    }

    public ChatRoom findByName(String name) {
        return chatRoomRepository.findByName(name);
    }

    @Transactional
    public ChatRoom createChatRoom(String name) {
        ChatRoom room = chatRoomCustomRepository.createRoom(name);
        chatRoomRepository.save(room);
        return room;
    }
    public Set<Member> findAllMembers(String roomUID){
        ChatRoom chatRoom = chatRoomCustomRepository.finByRoomId(roomUID);
        Team team = chatRoom.getGroup();
        return team.getMembers();
    }



}
