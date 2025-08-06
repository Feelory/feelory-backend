package com.feelory.feelory_backend.writings.dto.response;

import com.feelory.feelory_backend.writings.dto.model.WritingGoal;
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
    private List<WritingGoal> writingGoals;

    public static WritingGoalListResponse fromPage(Page<WritingGoals> page) {
        List<WritingGoal> writingGoalList = page.getContent().stream()
                .map(WritingGoal::fromEntity)
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
