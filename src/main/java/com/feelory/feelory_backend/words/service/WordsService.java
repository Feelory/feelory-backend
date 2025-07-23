package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.global.exception.exceptions.words.CategoryNotFoundException;
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

@Service
@RequiredArgsConstructor
public class WordsService {

    private final WordsRepository wordsRepository;
    private final CategoriesRepository categoriesRepository;

    public WordListResponse getWords(WordListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Words> words = wordsRepository.searchWords(request.getCategoryId(), request.getIsActive(), pageable);

        return WordListResponse.fromPage(words);
    }

    public WordCreateResponse registerWord(WordCreateRequest request) {

        WordCategories category = categoriesRepository.findByIdAndIsActive(request.getCategoryId(), true)
                .orElseThrow(CategoryNotFoundException::new);

        Words entity = request.toEntity(category);
        Words createdWord = wordsRepository.save(entity);

        Word word = Word.fromEntity(createdWord);

        return WordCreateResponse.builder()
                .word(word)
                .build();
    }
}
