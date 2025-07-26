package com.feelory.feelory_backend.writings.service;

import com.feelory.feelory_backend.global.exception.exceptions.common.DayTooFarInFutureException;
import com.feelory.feelory_backend.global.exception.exceptions.common.DayTooFarInPastException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateWritingGoalNameException;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingGoalNotFoundException;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.model.*;
import com.feelory.feelory_backend.writings.repository.WritingGoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @Transactional
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

    @Transactional
    public WritingGoalUpdateResponse modifyWritingGoal(WritingGoalUpdateRequest request) {

        WritingGoals entity = writingGoalsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoals.WritingGoalsBuilder builder = entity.toBuilder();

        if (request.getName() != null && !request.getName().isBlank()) {
            checkDuplicateName(entity.getUserId(), request.getName());
            builder.name(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            builder.description(request.getDescription());
        }

        if (request.getStartDate() != null) {
            LocalDateTime parsed = request.getParsedStartDate();
            validDate(parsed);
            builder.startDate(parsed);
        }

        if(request.getEndDate() != null) {
            LocalDateTime parsed = request.getParsedEndDate();
            validDate(parsed);
            builder.endDate(parsed);
        }

        WritingGoals updatedWritingGoals = builder.build();
        WritingGoals saved = writingGoalsRepository.save(updatedWritingGoals);
        writingGoalsRepository.flush();

        WritingGoals loaded = writingGoalsRepository.findByIdAndIsActive(saved.getId(), true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(loaded);

        return WritingGoalUpdateResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    @Transactional
    public WritingGoalDeleteResponse removeWritingGoal(WritingGoalDeleteRequest request) {
        WritingGoals entity = writingGoalsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoals updated = entity.toBuilder()
                .isActive(false)
                .build();

        writingGoalsRepository.save(updated);
        writingGoalsRepository.flush();

        WritingGoals loaded = writingGoalsRepository.findByIdAndIsActive(updated.getId(), true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(loaded);

        return WritingGoalDeleteResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    private void checkDuplicateName(Long userId, String name) {
        boolean isExist = writingGoalsRepository.existsByUserIdAndName(userId, name);

        if(isExist) {
            throw new DuplicateWritingGoalNameException();
        }
    }

    private void validDate(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();

        long monthsFromNow = ChronoUnit.MONTHS.between(now.withDayOfMonth(1), dateTime.withDayOfMonth(1));

        int beforeMonth = 6;
        if (monthsFromNow > beforeMonth) {
            throw new DayTooFarInPastException(beforeMonth);
        }

        int afterMonth = 12;
        if (monthsFromNow < -afterMonth) {
            throw new DayTooFarInFutureException(afterMonth);
        }
    }
}
