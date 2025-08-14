package com.feelory.feelory_backend.domain.writing.repository;

import com.feelory.feelory_backend.domain.writing.dto.model.WritingSearchDto;
import com.feelory.feelory_backend.domain.writing.entity.DailyWordWriting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DailyWordWritingRepositoryCustom {

    Page<DailyWordWriting> searchWritings(WritingSearchDto dto, Pageable pageable);

    Optional<DailyWordWriting> searchWritingDetailByDto(WritingSearchDto writingSearchDto);
}
