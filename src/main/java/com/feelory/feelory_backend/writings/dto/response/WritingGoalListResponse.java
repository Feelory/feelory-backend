package com.feelory.feelory_backend.writings.dto.response;

import com.feelory.feelory_backend.writings.dto.model.WritingGoalDto;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
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
public class WritingGoalListResponse {
    private Long total;
    private int page;
    private int size;
    private boolean hasNext;
    private List<WritingGoalDto> writingGoals;

    public static WritingGoalListResponse fromPage(Page<WritingGoals> page) {
        List<WritingGoalDto> writingGoalList = page.getContent().stream()
                .map(WritingGoalDto::fromEntity)
                .toList();

        return WritingGoalListResponse.builder()
                .total(page.getTotalElements())
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .writingGoals(writingGoalList)
                .build();
    }
}
