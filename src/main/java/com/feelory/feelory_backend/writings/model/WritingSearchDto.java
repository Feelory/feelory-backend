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

    public static WritingSearchDto fromListRequest(UserWritingListRequest request, Long userId) {

        return WritingSearchDto.builder()
                .userId(userId)
                .searchDate(request.getSearchDate())
                .isActive(true)
                .build();
    }

    public static WritingSearchDto fromUserId(Long userId) {

        LocalDateTime today = LocalDateTime.now();

        return WritingSearchDto.builder()
                .userId(userId)
                .searchDate(today)
                .isActive(true)
                .build();
    }
}
