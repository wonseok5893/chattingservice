package com.playground.th.repository;

import com.playground.th.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile,Long> {
}
