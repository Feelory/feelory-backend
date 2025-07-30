package com.feelory.feelory_backend.words.model;

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
