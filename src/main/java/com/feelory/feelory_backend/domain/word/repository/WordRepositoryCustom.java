package com.feelory.feelory_backend.domain.word.repository;

import com.feelory.feelory_backend.domain.word.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WordRepositoryCustom {

    Page<Word> searchWords(Long categoryId, Boolean isActive, Pageable pageable);
}
