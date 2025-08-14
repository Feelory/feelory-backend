package com.feelory.feelory_backend.domain.feedback.service;

import com.feelory.feelory_backend.domain.feedback.entity.Feedback;
import com.feelory.feelory_backend.domain.feedback.model.request.FeedbackRequest;
import com.feelory.feelory_backend.domain.feedback.model.response.FeedbackResponse;
import com.feelory.feelory_backend.domain.feedback.repository.FeedbackRepository;
import com.feelory.feelory_backend.global.exception.exceptions.writings.WritingNotFoundException;
import com.feelory.feelory_backend.global.webclient.GenerateContent;
import com.feelory.feelory_backend.global.webclient.dto.request.GenerateContentRequest;
import com.feelory.feelory_backend.global.webclient.dto.reponse.GenerateContentResponse;
import com.feelory.feelory_backend.domain.writing.entity.DailyWordWriting;
import com.feelory.feelory_backend.domain.writing.repository.DailyWordWritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final DailyWordWritingRepository dailyWordWritingsRepository;
    private final GenerateContent geminiGenerateContent;

    @Transactional
    public FeedbackResponse createFeedback(FeedbackRequest request) {
        DailyWordWriting writing = findDailyWriting(request);

        GenerateContentRequest feedbackRequest = buildGenerateContentRequest(writing);

        GenerateContentResponse feedbackResponse = generateContentFromModel(feedbackRequest);

        String feedbackText = extractFeedbackText(feedbackResponse);

        saveFeedback(feedbackText, writing);

        return new FeedbackResponse(feedbackText);
    }

    private DailyWordWriting findDailyWriting(FeedbackRequest request) {
        return dailyWordWritingsRepository.findById(request.getDailyWritingId())
                .orElseThrow(WritingNotFoundException::new);
    }

    private GenerateContentRequest buildGenerateContentRequest(DailyWordWriting writings) {
        return GenerateContentRequest.ofText(writings.getContent());
    }

    private GenerateContentResponse generateContentFromModel(GenerateContentRequest feedbackRequest) {
        return geminiGenerateContent.generate(feedbackRequest);
    }

    private String extractFeedbackText(GenerateContentResponse feedbackResponse) {
        GenerateContentResponse.Candidate candidate = feedbackResponse.candidates.get(0);

        GenerateContentResponse.Candidate.Content.Part part = candidate.content.parts.get(0);

        return part.text;
    }

    private void saveFeedback(String feedbackText, DailyWordWriting writing) {
        Feedback newFeedback = Feedback.builder()
                .content(feedbackText)
                .isActive(true)
                .build();

        writing.addFeedbacks(newFeedback);

        feedbackRepository.save(newFeedback);
    }

}
