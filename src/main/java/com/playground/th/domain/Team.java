package com.playground.th.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    private String name;
    private String content;

    private String location;
    private String category;
    private int maxMemberCount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_admin_id")
    private Member teamAdmin;
    private LocalDateTime createdTime;

    @ManyToMany
    @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> members = new HashSet<>();

    @PrePersist
    public void createdAt() {
        this.createdTime = LocalDateTime.now();
    }

    //생성 메서드
    public static Team createTeam(
            String name, String description, String location, String category
            ,int maxMemberCount, Member teamAdmin
    ) {
        Team team = new Team();
        team.setName(name);
        team.setContent(description);
        team.setLocation(location);
        team.setCategory(category);
        team.setMaxMemberCount(maxMemberCount);
        team.setTeamAdmin(teamAdmin);
        return team;
    }
    // 회원 추가
    public Member addMember(Member member){
        return null;
    }

}
