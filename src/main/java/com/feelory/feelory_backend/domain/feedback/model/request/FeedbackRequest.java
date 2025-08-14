package com.feelory.feelory_backend.domain.feedback.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {
    private Long dailyWritingId;
}
