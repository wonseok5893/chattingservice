package com.playground.th.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playground.th.exception.ExistMemberInTeam;
import com.playground.th.exception.NotEnoughMemberSize;
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
    private int currentMemberCount;
    private int maxMemberCount;

    private String teamImageUrl;
    private String startDate;
    private String endDate;

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
            ,int maxMemberCount,String teamImageUrl,String startDate,String endDate, Member teamAdmin, ChatRoom chatRoom
    ) {
        Team team = new Team();
        team.setName(name);
        team.setContent(description);
        team.setLocation(location);
        team.setCategory(category);
        team.setMaxMemberCount(maxMemberCount);
        team.setCurrentMemberCount(1);
        team.setTeamImageUrl(teamImageUrl);
        team.setStartDate(startDate);
        team.setEndDate(endDate);
        team.setTeamAdmin(teamAdmin);
        team.setChatRoom(chatRoom);
        return team;
    }
    // 회원 추가
    public boolean addMember(Member member) throws Exception{
        try {
            if(members.contains(member)) throw new ExistMemberInTeam(member.getEmail());
            if (maxMemberCount <= currentMemberCount) throw new NotEnoughMemberSize(this.name);
            members.add(member);
            currentMemberCount++;
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
