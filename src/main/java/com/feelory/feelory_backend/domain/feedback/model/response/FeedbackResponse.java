package com.feelory.feelory_backend.domain.feedback.model.response;

import com.feelory.feelory_backend.global.webclient.dto.model.GeminiFeedbackForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Integer score;
    private String content;

    public static FeedbackResponse fromForm(GeminiFeedbackForm form) {

        return new FeedbackResponse(form.getScore(), form.getContent());
    }
}
