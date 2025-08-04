package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.dto.model.WritingSearch;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DailyWordWritingsRepositoryCustom {

    Page<DailyWordWritings> searchWritings(WritingSearch dto, Pageable pageable);

    Optional<DailyWordWritings> searchWritingDetailByDto(WritingSearch writingSearchDto);
}
