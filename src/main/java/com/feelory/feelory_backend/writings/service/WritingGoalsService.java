package com.feelory.feelory_backend.writings.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.WritingGoalNotFoundException;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.model.WritingGoalDetailResponse;
import com.feelory.feelory_backend.writings.model.WritingGoalDto;
import com.feelory.feelory_backend.writings.model.WritingGoalSearchDto;
import com.feelory.feelory_backend.writings.repository.WritingGoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WritingGoalsService {

    private final WritingGoalsRepository writingGoalsRepository;

    public WritingGoalDetailResponse getWritingGoalDetail(LocalDateTime date, Long userId) {

        WritingGoalSearchDto dto = WritingGoalSearchDto.builder()
                .userId(userId)
                .searchDate(date)
                .isActive(true)
                .build();

        WritingGoals entity = writingGoalsRepository.searchWritingGoalDetailByDto(dto)
                .orElseThrow(WritingGoalNotFoundException::new);
        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(entity);

        return WritingGoalDetailResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }
}
