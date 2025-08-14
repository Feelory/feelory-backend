package com.feelory.feelory_backend.writing.dto.response;

import com.feelory.feelory_backend.writing.dto.model.WritingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWritingCreateResponse {
    private WritingDto writing;
}
