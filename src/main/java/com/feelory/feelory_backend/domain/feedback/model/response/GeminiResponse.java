package com.feelory.feelory_backend.domain.feedback.model.response;

import com.feelory.feelory_backend.domain.feedback.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponse {
    private String content;
    private List<Question> questions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Question{
        private String content;
        private String questionType;
    }
}

