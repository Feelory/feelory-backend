package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.words.entity.Words;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordListResponse {
    private Long total;
    private int page;
    private int size;
    private boolean hasNext;
    private List<WordDto> words;

    public static WordListResponse fromPage(Page<Words> page) {
        List<WordDto> wordList = page.getContent().stream()
                .map(WordDto::fromEntity)
                .toList();

        return WordListResponse.builder()
                .total(page.getTotalElements())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .words(wordList)
                .build();
    }
}
