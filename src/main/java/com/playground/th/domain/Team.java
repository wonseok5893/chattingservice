package com.playground.th.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Entity
@Setter @Getter
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="chatroom_id")
    private ChatRoom chatRoom;

    private String name;
    private String description;

    @Embedded
    private Location location;
    private String category;

    @ManyToMany
    @JoinTable(name = "team_member", joinColumns = @JoinColumn(name="team_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Member> members = new HashSet<>();
}
