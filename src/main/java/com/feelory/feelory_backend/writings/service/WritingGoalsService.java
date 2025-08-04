package com.feelory.feelory_backend.writings.service;

import com.feelory.feelory_backend.global.exception.exceptions.common.DayTooFarInFutureException;
import com.feelory.feelory_backend.global.exception.exceptions.common.DayTooFarInPastException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateWritingGoalNameException;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingGoalNotFoundException;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.global.util.ValidationUtil;
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
    private final ValidationUtil validationUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public WritingGoalListResponse getWritingGoals(WritingGoalListRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        WritingGoalListSearchDto searchDto = WritingGoalListSearchDto.builder()
                .userId(userId)
                .pageable(pageable)
                .isValidDate(request.getIsValidDate())
                .isActive(true)
                .build();
        Page<WritingGoals> page = writingGoalsRepository.searchWritingGoalsByDto(searchDto);

        return WritingGoalListResponse.fromPage(page);
    }

    public WritingGoalDetailResponse getWritingGoalDetail(Long id) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        WritingGoals entity = writingGoalsRepository.findByIdAndUserId(id, userId)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(entity);

        return WritingGoalDetailResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    @Transactional
    public WritingGoalCreateResponse registerWritingGoal(WritingGoalCreateRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();
        checkDuplicateName(userId, request.getName());

        WritingGoals entity = request.toEntity();
        WritingGoals createdWritingGoal = writingGoalsRepository.save(entity);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(createdWritingGoal);

        return WritingGoalCreateResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    @Transactional
    public WritingGoalUpdateResponse modifyWritingGoal(WritingGoalUpdateRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        WritingGoals entity = writingGoalsRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoals.WritingGoalsBuilder builder = entity.toBuilder();

        if (validationUtil.hasText(request.getName())) {
            checkDuplicateName(userId, request.getName());
            builder.name(request.getName());
        }

        if (validationUtil.hasText(request.getDescription())) {
            builder.description(request.getDescription());
        }

        if(request.getDuration() != null) {
            LocalDateTime baseTime = entity.getCreatedAt();
            builder.duration(request.getDuration());
            builder.startDate(request.getStartDate(baseTime));
            builder.endDate(request.getEndDate(baseTime));
        }

        WritingGoals updatedWritingGoals = builder.build();
        WritingGoals saved = writingGoalsRepository.save(updatedWritingGoals);

        WritingGoals loaded = writingGoalsRepository.findByIdAndUserIdAndIsActive(saved.getId(), userId, true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(loaded);

        return WritingGoalUpdateResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    @Transactional
    public WritingGoalDeleteResponse removeWritingGoal(WritingGoalDeleteRequest request) {
        Long userId = jwtTokenProvider.getUserIdFromAuthentication();
        WritingGoals entity = writingGoalsRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoals updated = entity.toBuilder()
                .isActive(false)
                .build();

        writingGoalsRepository.save(updated);

        WritingGoals loaded = writingGoalsRepository.findByIdAndUserIdAndIsActive(updated.getId(), userId,false)
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
