package com.feelory.feelory_backend.domain.word.dto.response;

import com.feelory.feelory_backend.domain.word.dto.model.WordDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordCreateResponse {
    private WordDto word;
}
