package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.model.WritingSearchDto;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DailyWordWritingsRepositoryCustom {

    Page<DailyWordWritings> searchWritings(Long userId, Boolean isActive, Pageable pageable);

    Optional<DailyWordWritings> searchWritingDetailByDto(WritingSearchDto writingSearchDto);
}
