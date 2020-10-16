package com.playground.th.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Embedded
    private Location location;
    private String category;
    private int maxMemberCount;

    @ManyToMany
    @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> members = new HashSet<>();

    //생성 메서드
    public static Team createTeam(
            String name, String description, String city, String street, String category
            ,int maxMemberCount
    ) {
        Team team = new Team();
        team.setName(name);
        team.setContent(description);
        team.setLocation(new Location(city, street));
        team.setCategory(category);
        team.setMaxMemberCount(maxMemberCount);
        return team;
    }

}
