package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.DailyWordNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.InvalidTopicDateException;
import com.feelory.feelory_backend.global.exception.exceptions.words.WordNotFoundException;
import com.feelory.feelory_backend.words.entity.DailyWords;
import com.feelory.feelory_backend.words.entity.Words;
import com.feelory.feelory_backend.words.model.DailyWordCreateRequest;
import com.feelory.feelory_backend.words.model.DailyWordCreateResponse;
import com.feelory.feelory_backend.words.model.DailyWordDto;
import com.feelory.feelory_backend.words.repository.DailyWordsRepository;
import com.feelory.feelory_backend.words.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyWordsService {

    private final DailyWordsRepository dailyWordsRepository;
    private final WordsRepository wordsRepository;


    public DailyWordCreateResponse registerAndUpdateDailyWord(DailyWordCreateRequest request) {

        boolean isBeforeDate = request.getTopicDate().toLocalDate().isBefore(LocalDate.now());
        if (isBeforeDate) throw new InvalidTopicDateException();


        DailyWords duplicatedDailyWord = dailyWordsRepository.findByTopicDateAndIsActive(request.getTopicDate(), true);

        boolean isAlreadyAssigned = false;
        DailyWordDto dailyWord = null;

        if(duplicatedDailyWord != null && !request.getIsReplaceApproved()) {

            isAlreadyAssigned = true;
        } else if(duplicatedDailyWord!= null) {

            isAlreadyAssigned = true;
            dailyWord = updateDailyWord(request.getWordId(), duplicatedDailyWord);
        } else {

            dailyWord = createDailyWord(request);
        }


        return DailyWordCreateResponse.builder()
                .isAlreadyAssigned(isAlreadyAssigned)
                .dailyWord(dailyWord)
                .build();
    }

    private DailyWordDto createDailyWord(DailyWordCreateRequest request) {
        Words word = wordsRepository.findById(request.getWordId())
                .orElseThrow(DailyWordNotFoundException::new);
        DailyWords newDailyWord = request.toEntity(word);

        DailyWords saved = dailyWordsRepository.save(newDailyWord);
        DailyWords loaded = dailyWordsRepository.findByIdAndIsActive(saved.getId(), true)
                .orElseThrow(DailyWordNotFoundException::new);

        return DailyWordDto.fromEntity(loaded);
    }

    private DailyWordDto updateDailyWord(Long wordId, DailyWords dailyWord) {
        Words word = wordsRepository.findById(wordId)
                .orElseThrow(DailyWordNotFoundException::new);

        DailyWords updatedEntity = dailyWord.toBuilder()
                .word(word)
                .build();

        DailyWords updated = dailyWordsRepository.save(updatedEntity);

        DailyWords loaded = dailyWordsRepository.findByIdAndIsActive(updated.getId(), true)
                .orElseThrow(DailyWordNotFoundException::new);

        return DailyWordDto.fromEntity(loaded);
    }
}
