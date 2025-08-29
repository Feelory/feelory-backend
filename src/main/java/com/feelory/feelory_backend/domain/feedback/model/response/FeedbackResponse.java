package com.feelory.feelory_backend.domain.feedback.model.response;

import com.feelory.feelory_backend.global.webclient.dto.model.GeminiFeedbackForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private String content;
    private List<String> questions;
}
