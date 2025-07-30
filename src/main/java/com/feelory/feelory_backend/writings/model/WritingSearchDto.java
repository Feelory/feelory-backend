package com.feelory.feelory_backend.writings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingSearchDto {
    private Long userId;
    private LocalDateTime searchDate;
    private Boolean isActive;

    public static WritingSearchDto fromListRequest(UserWritingListRequest request) {

        return WritingSearchDto.builder()
                .userId(request.getUserId())
                .searchDate(request.getSearchDate())
                .isActive(true)
                .build();
    }

    public static WritingSearchDto fromTodayRequest(UserTodayWritingRequest request) {

        LocalDateTime today = LocalDateTime.now();

        return WritingSearchDto.builder()
                .userId(request.getUserId())
                .searchDate(today)
                .isActive(true)
                .build();
    }
}
