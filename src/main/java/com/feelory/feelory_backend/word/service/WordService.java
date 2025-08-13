package com.feelory.feelory_backend.word.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.CategoryNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateWordNameException;
import com.feelory.feelory_backend.global.exception.exceptions.words.WordNotFoundException;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import com.feelory.feelory_backend.word.dto.model.WordDto;
import com.feelory.feelory_backend.word.dto.request.WordCreateRequest;
import com.feelory.feelory_backend.word.dto.request.WordDeleteRequest;
import com.feelory.feelory_backend.word.dto.request.WordListRequest;
import com.feelory.feelory_backend.word.dto.request.WordUpdateRequest;
import com.feelory.feelory_backend.word.dto.response.WordCreateResponse;
import com.feelory.feelory_backend.word.dto.response.WordListResponse;
import com.feelory.feelory_backend.word.entity.Word;
import com.feelory.feelory_backend.word.entity.WordCategory;
import com.feelory.feelory_backend.word.repository.CategoryRepository;
import com.feelory.feelory_backend.word.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final CategoryRepository categoriesRepository;
    private final ValidationUtil validationUtil;

    public WordListResponse getWords(WordListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Word> words = wordRepository.searchWords(request.getCategoryId(), request.getIsActive(), pageable);

        return WordListResponse.fromPage(words);
    }

    @Transactional
    public WordCreateResponse registerWord(WordCreateRequest request) {
        checkDuplicateName(request.getName());

        WordCategory category = getCategory(request.getCategoryId());

        Word entity = request.toEntity(category);
        Word createdWord = wordRepository.save(entity);

        WordDto word = WordDto.fromEntity(createdWord);

        return WordCreateResponse.builder()
                .word(word)
                .build();
    }

    @Transactional
    public void modifyWord(WordUpdateRequest request) {

        Word entity = wordRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WordNotFoundException::new);

        Word.WordBuilder builder = entity.toBuilder();

        if (validationUtil.hasText(request.getName())) {
            checkDuplicateName(request.getName());
            builder.name(request.getName());
        }

        if (validationUtil.hasText(request.getDescription())) {
            builder.description(request.getDescription());
        }

        if (request.getCategoryId() != null) {
            WordCategory category = getCategory(request.getCategoryId());
            builder.category(category);
        }

        Word updatedWord = builder.build();
        wordRepository.save(updatedWord);
    }

    @Transactional
    public void removeWord(WordDeleteRequest request) {

        Word entity = wordRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WordNotFoundException::new);

        Word updated = entity.toBuilder()
                .isActive(false)
                .build();

        wordRepository.save(updated);
    }

    private WordCategory getCategory(Long categoryId) {

        return categoriesRepository.findByIdAndIsActive(categoryId, true)
                .orElseThrow(CategoryNotFoundException::new);
    }

    private void checkDuplicateName(String name) {
        boolean isExist = wordRepository.existsByName(name);

        if(isExist) {
            throw new DuplicateWordNameException();
        }
    }
}
