package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyWordWritingsRepository extends JpaRepository<DailyWordWritings, Long>, DailyWordWritingsRepositoryCustom {
}
