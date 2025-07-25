package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DailyWordWritingsRepositoryCustom {

    Page<DailyWordWritings> searchWritings(Long userId, Boolean isActive, Pageable pageable);
}
