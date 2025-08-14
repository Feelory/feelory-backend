package com.feelory.feelory_backend.domain.word.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordListRequest {
    private int page = 0;
    private int size = 10;
    private Long categoryId;
    private Boolean isActive = true;
    private String search;
}
