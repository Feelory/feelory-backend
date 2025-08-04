package com.feelory.feelory_backend.writings.service;

import com.feelory.feelory_backend.global.exception.exceptions.users.UserNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.writings.DailyWordConflictException;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingGoalConflictException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DailyWordNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingNotFoundException;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.users.repository.UsersRepository;
import com.feelory.feelory_backend.words.entity.DailyWords;
import com.feelory.feelory_backend.words.repository.DailyWordsRepository;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.model.WritingSearchDto;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import com.feelory.feelory_backend.writings.model.*;
import com.feelory.feelory_backend.writings.repository.DailyWordWritingsRepository;
import com.feelory.feelory_backend.writings.repository.WritingGoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WritingsService {

    private final UsersRepository usersRepository;
    private final DailyWordWritingsRepository dailyWordWritingsRepository;
    private final DailyWordsRepository dailyWordsRepository;
    private final WritingGoalsRepository writingGoalsRepository;
    private final ValidationUtil validationUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public UserWritingListResponse getUserWritings(UserWritingListRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        WritingSearchDto dto = WritingSearchDto.fromListRequest(request, userId);

        Page<DailyWordWritings> writings = dailyWordWritingsRepository.searchWritings(dto, pageable);

        return UserWritingListResponse.fromPage(writings);
    }

    public UserTodayWritingResponse getUserTodayWriting() {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();
        WritingSearchDto dto = WritingSearchDto.fromUserId(userId);
        DailyWordWritings entity = dailyWordWritingsRepository.searchWritingDetailByDto(dto)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(entity);

        return UserTodayWritingResponse.builder()
                .writing(writing)
                .build();
    }

    public UserWritingDetailResponse getUserWritingDetail(Long id) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();
        DailyWordWritings entity = dailyWordWritingsRepository.findByIdAndUserIdAndIsActive(id, userId, true)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(entity);

        return UserWritingDetailResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public UserWritingCreateResponse registerUserWriting(UserWritingCreateRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        Users user = usersRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        DailyWords dailyWord = dailyWordsRepository.findByIdAndIsActive(request.getDailyWordId(), true)
                .orElseThrow(DailyWordNotFoundException::new);
        WritingGoals writingGoal = writingGoalsRepository.findByIdAndUserIdAndIsActive(request.getWritingGoalId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        checkDuplicateDailyWord(userId, dailyWord);
        checkDuplicateWritingGoal(userId, writingGoal);

        DailyWordWritings entity = request.toEntity(user, dailyWord, writingGoal);

        DailyWordWritings created = dailyWordWritingsRepository.save(entity);

        WritingDto writing = WritingDto.fromEntity(created);

        return UserWritingCreateResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public UserWritingUpdateResponse modifyUserWriting(UserWritingUpdateRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();
        DailyWordWritings entity = dailyWordWritingsRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWritings.DailyWordWritingsBuilder builder = entity.toBuilder();

        if (validationUtil.hasText(request.getContent())) {
            builder.content(request.getContent());
        }

        if (request.getDailyWordId() != null) {
            DailyWords dailyWord = dailyWordsRepository.findByIdAndIsActive(request.getDailyWordId(), true)
                    .orElseThrow(DailyWordNotFoundException::new);

            builder.dailyWord(dailyWord);
        }

        if(request.getWritingGoalId() != null) {
            WritingGoals writingGoal = writingGoalsRepository.findByIdAndUserIdAndIsActive(request.getWritingGoalId(), userId, true)
                    .orElseThrow(WritingNotFoundException::new);

            checkDuplicateWritingGoal(userId, writingGoal);

            builder.writingGoal(writingGoal);
        }

        if(request.getVisibility() != null) {
            builder.visibility(request.getVisibility());
        }

        DailyWordWritings updated = builder.build();
        DailyWordWritings saved = dailyWordWritingsRepository.save(updated);

        DailyWordWritings loaded = dailyWordWritingsRepository.findByIdAndUserIdAndIsActive(saved.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(loaded);

        return UserWritingUpdateResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public UserWritingDeleteResponse removeUserWriting(UserWritingDeleteRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        DailyWordWritings entity = dailyWordWritingsRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWritings updated = entity.toBuilder()
                .isActive(false)
                .build();

        DailyWordWritings saved = dailyWordWritingsRepository.save(updated);

        DailyWordWritings loaded = dailyWordWritingsRepository.findByIdAndUserIdAndIsActive(saved.getId(), userId, false)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(loaded);

        return UserWritingDeleteResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public VisibilityUpdateResponse modifyVisibility(VisibilityUpdateRequest request) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        DailyWordWritings entity = dailyWordWritingsRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWritings updated = entity.toBuilder()
                .visibility(request.getVisibility())
                .build();

        DailyWordWritings saved = dailyWordWritingsRepository.save(updated);

        DailyWordWritings loaded = dailyWordWritingsRepository.findByIdAndUserIdAndIsActive(saved.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(loaded);

        return VisibilityUpdateResponse.builder()
                .writing(writing)
                .build();
    }

    private void checkDuplicateDailyWord(Long userId, DailyWords dailyWord) {
        boolean isExist = dailyWordWritingsRepository.existsByUserIdAndDailyWord(userId, dailyWord);

        if(isExist) {
            throw new DailyWordConflictException();
        }
    }

    private void checkDuplicateWritingGoal(Long userId, WritingGoals writingGoal) {
        boolean isExist = dailyWordWritingsRepository.existsByUserIdAndWritingGoal(userId, writingGoal);

        if(isExist) {
            throw new WritingGoalConflictException();
        }
    }
}
