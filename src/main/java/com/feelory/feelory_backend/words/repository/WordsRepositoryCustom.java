package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.Words;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WordsRepositoryCustom {

    Page<Words> searchWords(Long categoryId, Boolean isActive, Pageable pageable);

    Optional<Words> findByIdAndIsActive(Long id, Boolean isActive);
}
