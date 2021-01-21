package com.playground.th.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.th.chat.Message;
import com.playground.th.controller.dto.chat.ChatRoomMemberDTO;
import com.playground.th.domain.ChatRoom;
import com.playground.th.domain.Member;
import com.playground.th.domain.Team;
import com.playground.th.repository.ChatRoomCustomRepository;
import com.playground.th.repository.ChatRoomRepository;
import com.playground.th.repository.MemberRepository;
import com.playground.th.repository.TeamRepository;
import com.playground.th.security.exception.UserNotFoundException;
import com.playground.th.service.dto.findAllMembersQueryDto;
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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomCustomRepository chatRoomCustomRepository;
    private final MemberRepository memberRepository;

    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> allRooms = chatRoomRepository.findAll();
        Collections.reverse(allRooms);
        return allRooms;
    }

    public ChatRoom findByRoomId(String id) {
        return chatRoomCustomRepository.finByRoomId(id);
    }

    public ChatRoom findByName(String name) {
        return chatRoomRepository.findByName(name);
    }


    public Set<Member> findAllMembers(String roomUID) {
        ChatRoom chatRoom = chatRoomCustomRepository.finByRoomId(roomUID);
        return chatRoom.getJoinMembers();
    }


    public List<ChatRoomMemberDTO> findAllMyChatRooms(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        List<ChatRoomMemberDTO> collect = member.getChatRooms().stream().map(chatRoom -> new ChatRoomMemberDTO(chatRoom)).collect(Collectors.toList());
        return collect;
    }

    public List<findAllMembersQueryDto> findAllMembersById(String to) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(to)).get();
        return chatRoom.getJoinMembers().stream().map((member) -> new findAllMembersQueryDto(member.getId(), member.getToken())).collect(Collectors.toList());
    }

    @Transactional
    public ChatRoom createPersonChatRoom(Message message) {
        // from mem id to other id
        Member me = memberRepository.findById(message.getFrom()).orElseThrow(() -> new UserNotFoundException(message.getFrom()));
        Member other = memberRepository.findById(Long.valueOf(message.getTo())).orElseThrow(() -> new UserNotFoundException(message.getTo()));
        ChatRoom chatRoom = ChatRoom.uniCreate(other, me);
        return chatRoomRepository.save(chatRoom);
    }
}
