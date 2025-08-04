package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.dto.model.WritingSearchDto;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DailyWordWritingsRepositoryCustom {

    Page<DailyWordWritings> searchWritings(WritingSearchDto dto, Pageable pageable);

    Optional<DailyWordWritings> searchWritingDetailByDto(WritingSearchDto writingSearchDto);
}
