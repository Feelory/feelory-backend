package com.feelory.feelory_backend.word.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.DailyWordNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.InvalidTopicDateException;
import com.feelory.feelory_backend.global.exception.exceptions.words.WordAlreadyUsedException;
import com.feelory.feelory_backend.word.dto.model.DailyWordDto;
import com.feelory.feelory_backend.word.dto.request.DailyWordCreateRequest;
import com.feelory.feelory_backend.word.dto.request.DailyWordDeleteRequest;
import com.feelory.feelory_backend.word.dto.request.DailyWordUpdateRequest;
import com.feelory.feelory_backend.word.dto.response.DailyWordCreateResponse;
import com.feelory.feelory_backend.word.dto.response.DailyWordDetailResponse;
import com.feelory.feelory_backend.word.entity.DailyWord;
import com.feelory.feelory_backend.word.entity.Word;
import com.feelory.feelory_backend.word.repository.DailyWordRepository;
import com.feelory.feelory_backend.word.repository.WordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DailyWordService {

    private final DailyWordRepository dailyWordRepository;
    private final WordRepository wordRepository;

    @Transactional
    public DailyWordCreateResponse registerAndUpdateDailyWord(DailyWordCreateRequest request) {

        validateDateTime(request.getParsedTopicDate());
        checkDuplicateWordId(request.getWordId());

        DailyWord duplicatedDailyWord = dailyWordRepository.findByTopicDateAndIsActive(request.getParsedTopicDate(), true)
                .orElse(null);

        boolean isAlreadyAssigned = false;
        DailyWordDto dailyWord = null;

        if(duplicatedDailyWord != null && !request.getIsReplaceApproved()) {

            isAlreadyAssigned = true;
        } else if(duplicatedDailyWord!= null) {

            DailyWordUpdateRequest updateRequest = DailyWordUpdateRequest.builder()
                    .id(duplicatedDailyWord.getId())
                    .wordId(request.getWordId())
                    .topicDate(request.getTopicDate())
                    .description(request.getDescription())
                    .build();

            isAlreadyAssigned = true;
            dailyWord = updateDailyWord(updateRequest, duplicatedDailyWord);
        } else {

            dailyWord = createDailyWord(request);
        }


        return DailyWordCreateResponse.builder()
                .isAlreadyAssigned(isAlreadyAssigned)
                .dailyWord(dailyWord)
                .build();
    }

    public DailyWordDetailResponse getDailyWord(LocalDateTime topicDate) {

        return getDailyWordDetailResponse(topicDate);
    }

    public DailyWordDetailResponse getDailyWordToday() {

        LocalDateTime now = LocalDateTime.now();

        return getDailyWordDetailResponse(now);
    }

    @Transactional
    public void modifyDailyWord(DailyWordUpdateRequest request) {

        validateDateTime(request.getParsedTopicDate());
        checkDuplicateWordId(request.getWordId());

        DailyWord dailyWord = dailyWordRepository.findById(request.getId())
                .orElseThrow(DailyWordNotFoundException::new);

        updateDailyWord(request, dailyWord);
    }

    @Transactional
    public void removeDailyWord(DailyWordDeleteRequest request) {

        DailyWord entity = dailyWordRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(DailyWordNotFoundException::new);

        DailyWord updated = entity.toBuilder()
                .isActive(false)
                .build();

        dailyWordRepository.save(updated);
    }

    private void validateDateTime(LocalDateTime topicDate) {
        boolean isBeforeDate = topicDate.toLocalDate().isBefore(LocalDate.now());
        if (isBeforeDate) throw new InvalidTopicDateException();
    }

    private void checkDuplicateWordId(Long wordId) {

        boolean isExist = dailyWordRepository.existsByWordId(wordId);

        if(isExist){
            throw new WordAlreadyUsedException();
        }
    }

    private DailyWordDto createDailyWord(DailyWordCreateRequest request) {
        Word word = wordRepository.findById(request.getWordId())
                .orElseThrow(DailyWordNotFoundException::new);
        DailyWord newDailyWord = request.toEntity(word);

        DailyWord saved;

        saved = dailyWordRepository.save(newDailyWord);

        DailyWord loaded = dailyWordRepository.findByIdAndIsActive(saved.getId(), true)
                .orElseThrow(DailyWordNotFoundException::new);

        return DailyWordDto.fromEntity(loaded);
    }

    private DailyWordDto updateDailyWord(DailyWordUpdateRequest request, DailyWord existing) {
        Word word = null;
        if (request.getWordId() != null) {
            word = wordRepository.findById(request.getWordId())
                    .orElseThrow(DailyWordNotFoundException::new);
        }

        DailyWord.DailyWordBuilder builder = existing.toBuilder();

        if (word != null) builder.word(word);
        if (request.getTopicDate() != null) builder.topicDate(request.getParsedTopicDate());
        if (request.getDescription() != null) builder.description(request.getDescription());

        DailyWord updated = dailyWordRepository.save(builder.build());

        DailyWord loaded = dailyWordRepository.findByIdAndIsActive(updated.getId(), true)
                .orElseThrow(DailyWordNotFoundException::new);

        return DailyWordDto.fromEntity(loaded);
    }

    private DailyWordDetailResponse getDailyWordDetailResponse(LocalDateTime dateTime) {

        DailyWord entity = dailyWordRepository.findByTopicDateAndIsActive(dateTime, true)
                .orElseThrow(DailyWordNotFoundException::new);

        DailyWordDto dailyWord = DailyWordDto.fromEntity(entity);

        return DailyWordDetailResponse.builder()
                .dailyWord(dailyWord)
                .build();
    }
}
