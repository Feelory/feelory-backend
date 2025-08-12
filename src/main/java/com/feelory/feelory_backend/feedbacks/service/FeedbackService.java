package com.feelory.feelory_backend.feedbacks.service;

import com.feelory.feelory_backend.feedbacks.entity.Feedbacks;
import com.feelory.feelory_backend.feedbacks.model.request.FeedbackRequest;
import com.feelory.feelory_backend.feedbacks.model.response.FeedbackResponse;
import com.feelory.feelory_backend.feedbacks.repository.FeedbackRepository;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingNotFoundException;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import com.feelory.feelory_backend.writings.repository.DailyWordWritingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final DailyWordWritingsRepository dailyWordWritingsRepository;

    @Transactional
    public FeedbackResponse createFeedback(FeedbackRequest request) {
        DailyWordWritings dailyWordWritings = dailyWordWritingsRepository.findById(request.getDailyWritingId())
                .orElseThrow(WritingNotFoundException::new);

        // 외부 API와 연동한 클래스에 전달하고 받아오기
        String feedbackContent = "AI 에게 받아올 피드백 내용";

        Feedbacks newFeedbacks = buildFeedbacks(feedbackContent, dailyWordWritings);

        feedbackRepository.save(newFeedbacks);

        return new FeedbackResponse(feedbackContent);
    }

    private Feedbacks buildFeedbacks(String feedbackContent, DailyWordWritings dailyWordWritings) {
        Feedbacks newFeedbacks = Feedbacks.builder()
                .content(feedbackContent)
                .isActive(true)
                .build();

        dailyWordWritings.addFeedbacks(newFeedbacks);
        return newFeedbacks;
    }
}
