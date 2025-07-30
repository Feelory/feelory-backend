package com.feelory.feelory_backend.writings.service;

import com.feelory.feelory_backend.global.exception.exceptions.writings.DailyWordConflictException;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingGoalConflictException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DailyWordNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingNotFoundException;
import com.feelory.feelory_backend.global.util.ValidationUtil;
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

    private final DailyWordWritingsRepository dailyWordWritingsRepository;
    private final DailyWordsRepository dailyWordsRepository;
    private final WritingGoalsRepository writingGoalsRepository;
    private final ValidationUtil validationUtil;

    public UserWritingListResponse getUserWritings(UserWritingListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DailyWordWritings> writings = dailyWordWritingsRepository.searchWritings(request.getUserId(), true, pageable);

        return UserWritingListResponse.fromPage(writings);
    }

    public UserTodayWritingResponse getUserTodayWriting(UserTodayWritingRequest request) {

        WritingSearchDto dto = WritingSearchDto.fromTodayRequest(request);
        DailyWordWritings entity = dailyWordWritingsRepository.searchWritingDetailByDto(dto)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(entity);

        return UserTodayWritingResponse.builder()
                .writing(writing)
                .build();
    }

    public UserWritingDetailResponse getUserWritingDetail(Long id) {

        DailyWordWritings entity = dailyWordWritingsRepository.findById(id)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(entity);

        return UserWritingDetailResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public UserWritingCreateResponse registerUserWriting(UserWritingCreateRequest request) {

        DailyWords dailyWord = dailyWordsRepository.findByIdAndIsActive(request.getDailyWordId(), true)
                .orElseThrow(DailyWordNotFoundException::new);
        WritingGoals writingGoal = writingGoalsRepository.findByIdAndIsActive(request.getWritingGoalId(), true)
                .orElseThrow(WritingNotFoundException::new);

        checkDuplicateDailyWord(request.getUserId(), dailyWord);
        checkDuplicateWritingGoal(request.getUserId(), writingGoal);

        DailyWordWritings entity = request.toEntity(dailyWord, writingGoal);

        DailyWordWritings created = dailyWordWritingsRepository.save(entity);
        dailyWordWritingsRepository.flush();

        WritingDto writing = WritingDto.fromEntity(created);

        return UserWritingCreateResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public UserWritingUpdateResponse modifyUserWriting(UserWritingUpdateRequest request) {

        DailyWordWritings entity = dailyWordWritingsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WritingNotFoundException::new);

        // TODO. [TR-YOO] 로그인 기능 반영 후 제거하기
        Long userId = entity.getUserId();

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
            WritingGoals writingGoal = writingGoalsRepository.findByIdAndIsActive(request.getWritingGoalId(), true)
                    .orElseThrow(WritingNotFoundException::new);

            checkDuplicateWritingGoal(userId, writingGoal);

            builder.writingGoal(writingGoal);
        }

        if(request.getVisibility() != null) {
            builder.visibility(request.getVisibility());
        }

        DailyWordWritings updated = builder.build();
        DailyWordWritings saved = dailyWordWritingsRepository.save(updated);
        dailyWordWritingsRepository.flush();

        DailyWordWritings loaded = dailyWordWritingsRepository.findByIdAndIsActive(saved.getId(), true)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(loaded);

        return UserWritingUpdateResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public UserWritingDeleteResponse removeUserWriting(UserWritingDeleteRequest request) {
        DailyWordWritings entity = dailyWordWritingsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWritings updated = entity.toBuilder()
                .isActive(false)
                .build();

        DailyWordWritings saved = dailyWordWritingsRepository.save(updated);
        dailyWordWritingsRepository.flush();

        DailyWordWritings loaded = dailyWordWritingsRepository.findByIdAndIsActive(saved.getId(), true)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(loaded);

        return UserWritingDeleteResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public VisibilityUpdateResponse modifyVisibility(VisibilityUpdateRequest request) {
        DailyWordWritings entity = dailyWordWritingsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWritings updated = entity.toBuilder()
                .visibility(request.getVisibility())
                .build();

        DailyWordWritings saved = dailyWordWritingsRepository.save(updated);
        dailyWordWritingsRepository.flush();

        DailyWordWritings loaded = dailyWordWritingsRepository.findByIdAndIsActive(saved.getId(), true)
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
