package com.playground.th.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ImageFile {
    @Id
    @GeneratedValue
    private Long id;
    private String fileUrl;
    private String fileSize;
    private LocalDateTime uploadTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
