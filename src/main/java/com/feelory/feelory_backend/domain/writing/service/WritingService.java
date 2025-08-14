package com.feelory.feelory_backend.domain.writing.service;

import com.feelory.feelory_backend.domain.user.entity.User;
import com.feelory.feelory_backend.domain.writing.dto.request.*;
import com.feelory.feelory_backend.domain.writing.dto.response.UserTodayWritingResponse;
import com.feelory.feelory_backend.domain.writing.dto.response.UserWritingCreateResponse;
import com.feelory.feelory_backend.domain.writing.dto.response.UserWritingDetailResponse;
import com.feelory.feelory_backend.domain.writing.dto.response.UserWritingListResponse;
import com.feelory.feelory_backend.global.exception.exceptions.user.UserNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.writing.DailyWordConflictException;
import com.feelory.feelory_backend.global.exception.exceptions.writing.WritingGoalConflictException;
import com.feelory.feelory_backend.global.exception.exceptions.word.DailyWordNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.writing.WritingNotFoundException;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import com.feelory.feelory_backend.domain.user.repository.UserRepository;
import com.feelory.feelory_backend.domain.word.entity.DailyWord;
import com.feelory.feelory_backend.domain.word.repository.DailyWordRepository;
import com.feelory.feelory_backend.domain.writing.dto.model.WritingDto;
import com.feelory.feelory_backend.domain.writing.entity.DailyWordWriting;
import com.feelory.feelory_backend.domain.writing.entity.WritingGoal;
import com.feelory.feelory_backend.domain.writing.dto.model.WritingSearchDto;
import com.feelory.feelory_backend.domain.writing.repository.DailyWordWritingRepository;
import com.feelory.feelory_backend.domain.writing.repository.WritingGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WritingService {

    private final UserRepository userRepository;
    private final DailyWordWritingRepository dailyWordWritingRepository;
    private final DailyWordRepository dailyWordRepository;
    private final WritingGoalRepository writingGoalRepository;
    private final ValidationUtil validationUtil;
    private final JwtProvider jwtProvider;

    public UserWritingListResponse getUserWritings(UserWritingListRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        WritingSearchDto dto = WritingSearchDto.fromListRequest(request, userId);

        Page<DailyWordWriting> writings = dailyWordWritingRepository.searchWritings(dto, pageable);

        return UserWritingListResponse.fromPage(writings);
    }

    public UserTodayWritingResponse getUserTodayWriting() {

        Long userId = jwtProvider.getUserIdFromAuthentication();
        WritingSearchDto dto = WritingSearchDto.fromUserId(userId);
        DailyWordWriting entity = dailyWordWritingRepository.searchWritingDetailByDto(dto)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(entity);

        return UserTodayWritingResponse.builder()
                .writing(writing)
                .build();
    }

    public UserWritingDetailResponse getUserWritingDetail(Long id) {

        Long userId = jwtProvider.getUserIdFromAuthentication();
        DailyWordWriting entity = dailyWordWritingRepository.findByIdAndUserIdAndIsActive(id, userId, true)
                .orElseThrow(WritingNotFoundException::new);

        WritingDto writing = WritingDto.fromEntity(entity);

        return UserWritingDetailResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public UserWritingCreateResponse registerUserWriting(UserWritingCreateRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        DailyWord dailyWord = dailyWordRepository.findByIdAndIsActive(request.getDailyWordId(), true)
                .orElseThrow(DailyWordNotFoundException::new);
        WritingGoal writingGoal = writingGoalRepository.findByIdAndUserIdAndIsActive(request.getWritingGoalId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        checkDuplicateDailyWord(userId, dailyWord);
        checkDuplicateWritingGoal(userId, writingGoal);

        DailyWordWriting entity = request.toEntity(user, dailyWord, writingGoal);

        DailyWordWriting created = dailyWordWritingRepository.save(entity);

        WritingDto writing = WritingDto.fromEntity(created);

        return UserWritingCreateResponse.builder()
                .writing(writing)
                .build();
    }

    @Transactional
    public void modifyUserWriting(UserWritingUpdateRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();
        DailyWordWriting entity = dailyWordWritingRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWriting.DailyWordWritingBuilder builder = entity.toBuilder();

        if (validationUtil.hasText(request.getContent())) {
            builder.content(request.getContent());
        }

        if (request.getDailyWordId() != null) {
            DailyWord dailyWord = dailyWordRepository.findByIdAndIsActive(request.getDailyWordId(), true)
                    .orElseThrow(DailyWordNotFoundException::new);

            builder.dailyWord(dailyWord);
        }

        if(request.getWritingGoalId() != null) {
            WritingGoal writingGoal = writingGoalRepository.findByIdAndUserIdAndIsActive(request.getWritingGoalId(), userId, true)
                    .orElseThrow(WritingNotFoundException::new);

            checkDuplicateWritingGoal(userId, writingGoal);

            builder.writingGoal(writingGoal);
        }

        if(request.getVisibility() != null) {
            builder.visibility(request.getVisibility());
        }

        DailyWordWriting updated = builder.build();
        dailyWordWritingRepository.save(updated);
    }

    @Transactional
    public void removeUserWriting(UserWritingDeleteRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        DailyWordWriting entity = dailyWordWritingRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWriting updated = entity.toBuilder()
                .isActive(false)
                .build();

        dailyWordWritingRepository.save(updated);
    }

    @Transactional
    public void modifyVisibility(VisibilityUpdateRequest request) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        DailyWordWriting entity = dailyWordWritingRepository.findByIdAndUserIdAndIsActive(request.getId(), userId, true)
                .orElseThrow(WritingNotFoundException::new);

        DailyWordWriting updated = entity.toBuilder()
                .visibility(request.getVisibility())
                .build();

        dailyWordWritingRepository.save(updated);
    }

    private void checkDuplicateDailyWord(Long userId, DailyWord dailyWord) {
        boolean isExist = dailyWordWritingRepository.existsByUserIdAndDailyWord(userId, dailyWord);

        if(isExist) {
            throw new DailyWordConflictException();
        }
    }

    private void checkDuplicateWritingGoal(Long userId, WritingGoal writingGoal) {
        boolean isExist = dailyWordWritingRepository.existsByUserIdAndWritingGoal(userId, writingGoal);

        if(isExist) {
            throw new WritingGoalConflictException();
        }
    }
}
