package com.feelory.feelory_backend.words.service;

import com.feelory.feelory_backend.words.entity.Words;
import com.feelory.feelory_backend.words.model.WordListRequest;
import com.feelory.feelory_backend.words.model.WordListResponse;
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

    public WordListResponse getWords(WordListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Words> words = wordsRepository.searchWords(request.getCategoryId(), request.getIsActive(), pageable);

        return WordListResponse.fromPage(words);
    }
}
