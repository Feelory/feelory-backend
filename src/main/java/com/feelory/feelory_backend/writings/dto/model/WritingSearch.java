package com.feelory.feelory_backend.writings.dto.model;

import com.feelory.feelory_backend.writings.dto.request.UserWritingListRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingSearch {
    private Long userId;
    private LocalDateTime searchDate;
    private Boolean isActive;

    public static WritingSearch fromListRequest(UserWritingListRequest request, Long userId) {

        return WritingSearch.builder()
                .userId(userId)
                .searchDate(request.getSearchDate())
                .isActive(true)
                .build();
    }

    public static WritingSearch fromUserId(Long userId) {

        LocalDateTime today = LocalDateTime.now();

        return WritingSearch.builder()
                .userId(userId)
                .searchDate(today)
                .isActive(true)
                .build();
    }
}
