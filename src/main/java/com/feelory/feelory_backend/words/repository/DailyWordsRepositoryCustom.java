package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.DailyWords;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DailyWordsRepositoryCustom {

    Optional<DailyWords> findByTopicDateAndIsActive(LocalDateTime topicDate, Boolean isActive);
}
