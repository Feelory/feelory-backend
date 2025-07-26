package com.feelory.feelory_backend.writings.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateWritingGoalNameException;
import com.feelory.feelory_backend.global.exception.exceptions.words.WritingGoalNotFoundException;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.model.*;
import com.feelory.feelory_backend.writings.repository.WritingGoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WritingGoalsService {

    private final WritingGoalsRepository writingGoalsRepository;

    public WritingGoalListResponse getWritingGoals(WritingGoalListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        WritingGoalListSearchDto searchDto = WritingGoalListSearchDto.builder()
                .userId(request.getUserId())
                .pageable(pageable)
                .isValidDate(request.getIsValidDate())
                .isActive(true)
                .build();
        Page<WritingGoals> page = writingGoalsRepository.searchWritingGoalsByDto(searchDto);

        return WritingGoalListResponse.fromPage(page);
    }

    public WritingGoalDetailResponse getWritingGoalDetail(Long id) {

        WritingGoals entity = writingGoalsRepository.findById(id)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(entity);

        return WritingGoalDetailResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    public WritingGoalCreateResponse registerWritingGoal(WritingGoalCreateRequest request) {

        checkDuplicateName(request.getUserId(), request.getName());

        WritingGoals entity = request.toEntity();
        WritingGoals createdWritingGoal = writingGoalsRepository.save(entity);
        writingGoalsRepository.flush();

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(createdWritingGoal);

        return WritingGoalCreateResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    private void checkDuplicateName(Long userId, String name) {
        boolean isExist = writingGoalsRepository.existsByUserIdAndName(userId, name);

        if(isExist) {
            throw new DuplicateWritingGoalNameException();
        }
    }
}
