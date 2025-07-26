package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.DailyWordNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.InvalidTopicDateException;
import com.feelory.feelory_backend.global.exception.exceptions.words.WordAlreadyUsedException;
import com.feelory.feelory_backend.words.entity.DailyWords;
import com.feelory.feelory_backend.words.entity.Words;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.repository.DailyWordsRepository;
import com.feelory.feelory_backend.words.repository.WordsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DailyWordsService {

    private final DailyWordsRepository dailyWordsRepository;
    private final WordsRepository wordsRepository;


    @Transactional
    public DailyWordCreateResponse registerAndUpdateDailyWord(DailyWordCreateRequest request) {

        validateDateTime(request.getTopicDate());
        checkDuplicateWordId(request.getWordId());

        DailyWords duplicatedDailyWord = dailyWordsRepository.findByTopicDateAndIsActive(request.getTopicDate(), true)
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
    public DailyWordUpdateResponse modifyDailyWord(DailyWordUpdateRequest request) {

        validateDateTime(request.getTopicDate());
        checkDuplicateWordId(request.getWordId());

        DailyWords dailyWords = dailyWordsRepository.findById(request.getId())
                .orElseThrow(DailyWordNotFoundException::new);

        DailyWordDto updated = updateDailyWord(request, dailyWords);

        return DailyWordUpdateResponse.builder()
                .dailyWord(updated)
                .build();
    }

    @Transactional
    public DailyWordDeleteResponse removeDailyWord(DailyWordDeleteRequest request) {

        DailyWords entity = dailyWordsRepository.findById(request.getId())
                .orElseThrow(DailyWordNotFoundException::new);

        DailyWords updated = entity.toBuilder()
                .isActive(false)
                .build();

        dailyWordsRepository.save(updated);
        dailyWordsRepository.flush();

        DailyWordDto dailyWord = DailyWordDto.fromEntity(updated);

        return DailyWordDeleteResponse.builder()
                .dailyWord(dailyWord)
                .build();
    }

    private void validateDateTime(LocalDateTime topicDate) {
        boolean isBeforeDate = topicDate.toLocalDate().isBefore(LocalDate.now());
        if (isBeforeDate) throw new InvalidTopicDateException();
    }

    private void checkDuplicateWordId(Long wordId) {

        boolean isExist = dailyWordsRepository.existsByWordId(wordId);

        if(isExist){
            throw new WordAlreadyUsedException();
        }
    }

    private DailyWordDto createDailyWord(DailyWordCreateRequest request) {
        Words word = wordsRepository.findById(request.getWordId())
                .orElseThrow(DailyWordNotFoundException::new);
        DailyWords newDailyWord = request.toEntity(word);

        DailyWords saved;

        saved = dailyWordsRepository.save(newDailyWord);
        dailyWordsRepository.flush();

        DailyWords loaded = dailyWordsRepository.findByIdAndIsActive(saved.getId(), true)
                .orElseThrow(DailyWordNotFoundException::new);

        return DailyWordDto.fromEntity(loaded);
    }

    private DailyWordDto updateDailyWord(DailyWordUpdateRequest request, DailyWords existing) {
        Words word = null;
        if (request.getWordId() != null) {
            word = wordsRepository.findById(request.getWordId())
                    .orElseThrow(DailyWordNotFoundException::new);
        }

        DailyWords.DailyWordsBuilder builder = existing.toBuilder();

        if (word != null) builder.word(word);
        if (request.getTopicDate() != null) builder.topicDate(request.getTopicDate());
        if (request.getDescription() != null) builder.description(request.getDescription());

        DailyWords updated = dailyWordsRepository.save(builder.build());
        dailyWordsRepository.flush();

        DailyWords loaded = dailyWordsRepository.findByIdAndIsActive(updated.getId(), true)
                .orElseThrow(DailyWordNotFoundException::new);

        return DailyWordDto.fromEntity(loaded);
    }

    private DailyWordDetailResponse getDailyWordDetailResponse(LocalDateTime dateTime) {

        DailyWords entity = dailyWordsRepository.findByTopicDateAndIsActive(dateTime, true)
                .orElseThrow(DailyWordNotFoundException::new);

        DailyWordDto dailyWord = DailyWordDto.fromEntity(entity);

        return DailyWordDetailResponse.builder()
                .dailyWord(dailyWord)
                .build();
    }
}
