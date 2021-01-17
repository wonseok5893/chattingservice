package com.playground.th.service;

import com.playground.th.Application;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.junit.jupiter.api.Assertions.*;

class ImageFileServiceTest {

    @Test
    void 파일경로(){
        String path = Application.class.getResource("").getPath();
        System.out.println(path);
    }


}