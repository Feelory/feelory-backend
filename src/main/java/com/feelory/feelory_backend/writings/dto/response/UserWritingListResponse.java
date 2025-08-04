package com.feelory.feelory_backend.writings.dto.response;

import com.feelory.feelory_backend.writings.dto.model.Writing;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
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
public class UserWritingListResponse {
    private Long total;
    private int page;
    private int size;
    private boolean hasNext;
    private List<Writing> writings;

    public static UserWritingListResponse fromPage(Page<DailyWordWritings> page) {
        List<Writing> writingList = page.getContent().stream()
                .map(com.feelory.feelory_backend.writings.dto.model.Writing::fromEntity)
                .toList();

        return UserWritingListResponse.builder()
                .total(page.getTotalElements())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .writings(writingList)
                .build();
    }
}
