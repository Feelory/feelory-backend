package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyWordWritingsRepository extends JpaRepository<DailyWordWritings, Long>, DailyWordWritingsRepositoryCustom {
}
