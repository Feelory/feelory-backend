package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.CategoryNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateWordNameException;
import com.feelory.feelory_backend.global.exception.exceptions.words.WordNotFoundException;
import com.feelory.feelory_backend.words.entity.WordCategories;
import com.feelory.feelory_backend.words.entity.Words;
import com.feelory.feelory_backend.words.model.*;
import com.feelory.feelory_backend.words.repository.CategoriesRepository;
import com.feelory.feelory_backend.words.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WordsService {

    private final WordsRepository wordsRepository;
    private final CategoriesRepository categoriesRepository;

    @Transactional(readOnly = true)
    public WordListResponse getWords(WordListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Words> words = wordsRepository.searchWords(request.getCategoryId(), request.getIsActive(), pageable);

        return WordListResponse.fromPage(words);
    }

    public WordCreateResponse registerWord(WordCreateRequest request) {

        checkDuplicateName(request.getName());

        WordCategories category = getCategory(request.getCategoryId());

        Words entity = request.toEntity(category);
        Words createdWord = wordsRepository.save(entity);

        Word word = Word.fromEntity(createdWord);

        return WordCreateResponse.builder()
                .word(word)
                .build();
    }

    public WordUpdateResponse modifyWord(WordUpdateRequest request) {

        Words entity = wordsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WordNotFoundException::new);

        Words.WordsBuilder builder = entity.toBuilder();

        if (request.getName() != null && !request.getName().isBlank()) {
            checkDuplicateName(request.getName());
            builder.name(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            builder.description(request.getDescription());
        }

        if (request.getCategoryId() != null) {
            WordCategories category = getCategory(request.getCategoryId());
            builder.category(category);
        }

        Words updatedWord = builder.build();
        Words saved = wordsRepository.save(updatedWord);

        Words loaded = wordsRepository.findByIdAndIsActive(saved.getId(), true)
                .orElseThrow(WordNotFoundException::new);

        Word word = Word.fromEntity(loaded);

        return WordUpdateResponse.builder()
                .word(word)
                .build();
    }

    public WordDeleteResponse removeWord(WordDeleteRequest request) {
        Words entity = wordsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WordNotFoundException::new);

        Words updatedEntity = entity.toBuilder()
                .isActive(false)
                .build();

        wordsRepository.save(updatedEntity);

        Word word = Word.fromEntity(updatedEntity);

        return WordDeleteResponse.builder()
                .word(word)
                .build();
    }

    private WordCategories getCategory(Long categoryId) {

        return categoriesRepository.findByIdAndIsActive(categoryId, true)
                .orElseThrow(CategoryNotFoundException::new);
    }

    private void checkDuplicateName(String name) {
        boolean isExist = wordsRepository.existsByName(name);

        if(isExist) {
            throw new DuplicateWordNameException();
        }
    }
}
