package com.feelory.feelory_backend.words.model;

import com.feelory.feelory_backend.writings.model.UserTodayWritingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingDetailDto {
    private Long userId;
    private LocalDateTime searchDate;
    private Boolean isActive;

    public static WritingDetailDto fromTodayRequest(UserTodayWritingRequest request) {

        LocalDateTime today = LocalDateTime.now();

        return WritingDetailDto.builder()
                .userId(request.getUserid())
                .searchDate(today)
                .isActive(true)
                .build();
    }
}
