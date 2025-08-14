package com.feelory.feelory_backend.domain.writing.service;

import com.feelory.feelory_backend.domain.user.entity.User;
import com.feelory.feelory_backend.domain.writing.dto.response.WritingGoalCreateResponse;
import com.feelory.feelory_backend.domain.writing.dto.response.WritingGoalDetailResponse;
import com.feelory.feelory_backend.domain.writing.dto.response.WritingGoalListResponse;
import com.feelory.feelory_backend.global.exception.exceptions.common.DayTooFarInFutureException;
import com.feelory.feelory_backend.global.exception.exceptions.common.DayTooFarInPastException;
import com.feelory.feelory_backend.global.exception.exceptions.user.UserNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.word.DuplicateWritingGoalNameException;
import com.feelory.feelory_backend.global.exception.exceptions.writing.WritingGoalNotFoundException;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import com.feelory.feelory_backend.domain.user.repository.UserRepository;
import com.feelory.feelory_backend.domain.writing.dto.model.WritingGoalDto;
import com.feelory.feelory_backend.domain.writing.dto.model.WritingGoalListSearchDto;
import com.feelory.feelory_backend.domain.writing.dto.request.WritingGoalCreateRequest;
import com.feelory.feelory_backend.domain.writing.dto.request.WritingGoalDeleteRequest;
import com.feelory.feelory_backend.domain.writing.dto.request.WritingGoalListRequest;
import com.feelory.feelory_backend.domain.writing.dto.request.WritingGoalUpdateRequest;
import com.feelory.feelory_backend.domain.writing.entity.WritingGoal;
import com.feelory.feelory_backend.domain.writing.repository.WritingGoalRepository;
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
public class WritingGoalService {

    private final WritingGoalRepository writingGoalRepository;
    private final ValidationUtil validationUtil;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public WritingGoalListResponse getWritingGoals(WritingGoalListRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        WritingGoalListSearchDto searchDto = WritingGoalListSearchDto.builder()
                .userId(userId)
                .pageable(pageable)
                .isValidDate(request.getIsValidDate())
                .isActive(true)
                .build();
        Page<WritingGoal> page = writingGoalRepository.searchWritingGoalsByDto(searchDto);

        return WritingGoalListResponse.fromPage(page);
    }

    public WritingGoalDetailResponse getWritingGoalDetail(Long id) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        WritingGoal entity = writingGoalRepository.findByIdAndUserId(id, userId)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(entity);

        return WritingGoalDetailResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    @Transactional
    public WritingGoalCreateResponse registerWritingGoal(WritingGoalCreateRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();
        checkDuplicateName(userId, request.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        WritingGoal entity = request.toEntity(user);
        WritingGoal createdWritingGoal = writingGoalRepository.save(entity);

        WritingGoalDto writingGoal = WritingGoalDto.fromEntity(createdWritingGoal);

        return WritingGoalCreateResponse.builder()
                .writingGoal(writingGoal)
                .build();
    }

    @Transactional
    public void modifyWritingGoal(WritingGoalUpdateRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        WritingGoal entity = writingGoalRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoal.WritingGoalBuilder builder = entity.toBuilder();

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

        WritingGoal updatedWritingGoal = builder.build();
        writingGoalRepository.save(updatedWritingGoal);
    }

    @Transactional
    public void removeWritingGoal(WritingGoalDeleteRequest request) {
        Long userId = jwtProvider.getUserIdFromAuthentication();
        WritingGoal entity = writingGoalRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingGoalNotFoundException::new);

        WritingGoal updated = entity.toBuilder()
                .isActive(false)
                .build();

        writingGoalRepository.save(updated);
    }

    private void checkDuplicateName(Long userId, String name) {
        boolean isExist = writingGoalRepository.existsByUserIdAndName(userId, name);

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
