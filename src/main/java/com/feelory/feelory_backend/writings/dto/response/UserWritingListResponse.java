package com.feelory.feelory_backend.writings.dto.response;

import com.feelory.feelory_backend.writings.dto.model.WritingDto;
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
    private List<WritingDto> writings;

    public static UserWritingListResponse fromPage(Page<DailyWordWritings> page) {
        List<WritingDto> writingList = page.getContent().stream()
                .map(WritingDto::fromEntity)
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
