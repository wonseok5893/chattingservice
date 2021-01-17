package com.playground.th.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter
@Entity
public class ImageFile {
    @Id
    @GeneratedValue
    private Long id;
    private String fileUrl;
    private String fileName;
    private LocalDateTime uploadTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @PrePersist
    public void createdAt() {
        this.uploadTime = LocalDateTime.now();
    }
    public static ImageFile createImageFile(String fileName,String fileUrl){
        ImageFile imageFile = new ImageFile();
        imageFile.setFileName(fileName);
        imageFile.setFileUrl(fileUrl);
        return imageFile;
    }
}
