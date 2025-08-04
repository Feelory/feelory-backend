package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.CategoryNotFoundException;
import com.feelory.feelory_backend.global.exception.exceptions.words.DuplicateWordNameException;
import com.feelory.feelory_backend.global.exception.exceptions.words.WordNotFoundException;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import com.feelory.feelory_backend.words.dto.model.Word;
import com.feelory.feelory_backend.words.dto.request.WordCreateRequest;
import com.feelory.feelory_backend.words.dto.request.WordDeleteRequest;
import com.feelory.feelory_backend.words.dto.request.WordListRequest;
import com.feelory.feelory_backend.words.dto.request.WordUpdateRequest;
import com.feelory.feelory_backend.words.dto.response.WordCreateResponse;
import com.feelory.feelory_backend.words.dto.response.WordDeleteResponse;
import com.feelory.feelory_backend.words.dto.response.WordListResponse;
import com.feelory.feelory_backend.words.dto.response.WordUpdateResponse;
import com.feelory.feelory_backend.words.entity.WordCategories;
import com.feelory.feelory_backend.words.entity.Words;
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
    private final ValidationUtil validationUtil;

    public WordListResponse getWords(WordListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Words> words = wordsRepository.searchWords(request.getCategoryId(), request.getIsActive(), pageable);

        return WordListResponse.fromPage(words);
    }

    @Transactional
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

    @Transactional
    public WordUpdateResponse modifyWord(WordUpdateRequest request) {

        Words entity = wordsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WordNotFoundException::new);

        Words.WordsBuilder builder = entity.toBuilder();

        if (validationUtil.hasText(request.getName())) {
            checkDuplicateName(request.getName());
            builder.name(request.getName());
        }

        if (validationUtil.hasText(request.getDescription())) {
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

    @Transactional
    public WordDeleteResponse removeWord(WordDeleteRequest request) {

        Words entity = wordsRepository.findByIdAndIsActive(request.getId(), true)
                .orElseThrow(WordNotFoundException::new);

        Words updated = entity.toBuilder()
                .isActive(false)
                .build();

        wordsRepository.save(updated);

        Words loaded = wordsRepository.findByIdAndIsActive(updated.getId(), false)
                .orElseThrow(WordNotFoundException::new);

        Word word = Word.fromEntity(loaded);


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
