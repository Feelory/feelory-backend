package com.feelory.feelory_backend.domain.word.repository;

import com.feelory.feelory_backend.domain.word.entity.DailyWord;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DailyWordRepositoryCustom {

    Optional<DailyWord> findByTopicDateAndIsActive(LocalDateTime topicDate, Boolean isActive);
}
