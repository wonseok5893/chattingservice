package com.playground.th.service;

import com.playground.th.controller.dto.MemberDto;
import com.playground.th.domain.ImageFile;
import com.playground.th.domain.Member;
import com.playground.th.exception.NotFoundFileException;
import com.playground.th.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ImageFileService {

    public static final String BASE_STORAGE_PATH = "C:\\Users\\wonseok\\Desktop\\7th1\\7th\\7th\\src\\main\\resources\\static\\image\\";
    public static final String ACCESS_FILE_PATH = "/upload/image/";
    public static final String STUDENT_CARD_STORAGE_PATH = "C:\\Users\\wonseok\\Desktop\\7th1\\7th\\7th\\src\\main\\resources\\static\\student\\";
    public static final String TEAM_IMAGE_STORAGE_PATH = "C:\\Users\\wonseok\\Desktop\\7th1\\7th\\7th\\src\\main\\resources\\static\\image\\";

    private final ImageFileRepository imageFileRepository;

    public String getStudentCardImageUrl(String email, String fileName) {
        return email + "_" + fileName;
    }

    public boolean saveTeamImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) return false;
        String fileName = file.getOriginalFilename();
        String filePath = TEAM_IMAGE_STORAGE_PATH + fileName;
        File newFile = new File(filePath);
        file.transferTo(newFile);
        return true;
    }

    public boolean deleteTeamImage(String deleteFileName) throws IOException {
        File deleteFile = new File(TEAM_IMAGE_STORAGE_PATH+deleteFileName);
        if (deleteFile.exists()) {
           return deleteFile.delete();
        } else {
            throw new NotFoundFileException(deleteFileName);
        }
    }

    public boolean saveStudentCard(Member member, MultipartFile file) throws IOException {
        if (file.isEmpty()) return false;
        String fileName = member.getEmail() + "_" + file.getOriginalFilename();
        String filePath = STUDENT_CARD_STORAGE_PATH + fileName;
        File newFile = new File(filePath);
        file.transferTo(newFile);
        return true;
    }

    @Transactional
    public boolean saveImageFile(MultipartFile file, Member member) throws IOException {
        //파일 저장
        if (!file.isEmpty()) {
            // ClassPathResource cpr = new ClassPathResource("");
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = BASE_STORAGE_PATH + fileName;

            File newFile = new File(filePath);
            file.transferTo(newFile);            // 이미지 파일 저장
            ImageFile newImageFile = ImageFile.createImageFile(fileName, ACCESS_FILE_PATH + fileName);

            // 멤버 저장
            newImageFile.setMember(member);
            imageFileRepository.save(newImageFile);
            return true;
        }
        //파일이 없음
        return false;
    }

}
