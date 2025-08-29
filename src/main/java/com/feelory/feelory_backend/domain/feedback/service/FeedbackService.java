package com.feelory.feelory_backend.domain.feedback.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelory.feelory_backend.domain.feedback.entity.Feedback;
import com.feelory.feelory_backend.domain.feedback.entity.FeedbackQuestion;
import com.feelory.feelory_backend.domain.feedback.model.QuestionType;
import com.feelory.feelory_backend.domain.feedback.model.request.FeedbackRequest;
import com.feelory.feelory_backend.domain.feedback.model.response.FeedbackResponse;
import com.feelory.feelory_backend.domain.feedback.model.response.GeminiResponse;
import com.feelory.feelory_backend.domain.feedback.repository.FeedbackQuestionRepository;
import com.feelory.feelory_backend.domain.feedback.repository.FeedbackRepository;
import com.feelory.feelory_backend.global.exception.exceptions.feedback.FeedbackParsingException;
import com.feelory.feelory_backend.global.exception.exceptions.writing.WritingNotFoundException;
import com.feelory.feelory_backend.global.webclient.GenerateContent;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiFeedbackForm;
import com.feelory.feelory_backend.global.webclient.dto.model.SystemInstructionProperties;
import com.feelory.feelory_backend.global.webclient.dto.request.GenerateContentRequest;
import com.feelory.feelory_backend.global.webclient.dto.reponse.GenerateContentResponse;
import com.feelory.feelory_backend.domain.writing.entity.DailyWordWriting;
import com.feelory.feelory_backend.domain.writing.repository.DailyWordWritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final DailyWordWritingRepository dailyWordWritingsRepository;
    private final GenerateContent geminiGenerateContent;
    private final SystemInstructionProperties systemInstructionProperties;
    private final FeedbackQuestionRepository feedbackQuestionRepository;

    @Transactional
    public FeedbackResponse createFeedback(FeedbackRequest request) {
        DailyWordWriting writing = findDailyWriting(request);

        GenerateContentRequest feedbackRequest = buildGenerateContentRequest(writing);

        GenerateContentResponse feedbackResponse = generateContentFromModel(feedbackRequest);

        String feedbackJson = extractFeedbackText(feedbackResponse);

        GeminiResponse geminiResponse = parsingGeminiResponse(feedbackJson);

        Feedback newFeedback = saveFeedback(geminiResponse.getContent(), writing);
        List<String> newQuestion = saveFeedbackQuestion(geminiResponse.getQuestions(),newFeedback);

        return new FeedbackResponse(newFeedback.getContent(),newQuestion);
    }

    private DailyWordWriting findDailyWriting(FeedbackRequest request) {
        return dailyWordWritingsRepository.findById(request.getDailyWritingId())
                .orElseThrow(WritingNotFoundException::new);
    }

    private GenerateContentRequest buildGenerateContentRequest(DailyWordWriting writings) {
        return GenerateContentRequest.ofText(writings.getContent(), systemInstructionProperties.getPrompts());
    }

    private GenerateContentResponse generateContentFromModel(GenerateContentRequest feedbackRequest) {
        return geminiGenerateContent.generate(feedbackRequest);
    }

    private String extractFeedbackText(GenerateContentResponse feedbackResponse) {
        GenerateContentResponse.Candidate candidate = feedbackResponse.candidates.get(0);

        GenerateContentResponse.Candidate.Content.Part part = candidate.content.parts.get(0);

        return part.text;
    }

    private Feedback saveFeedback(String feedbackText, DailyWordWriting writing) {
        Feedback newFeedback = Feedback.builder()
                .content(feedbackText)
                .isActive(true)
                .build();

        writing.addFeedbacks(newFeedback);

        return feedbackRepository.save(newFeedback);
    }

    private GeminiFeedbackForm parsingFeedbackForm(String json) {

        ObjectMapper om = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {

            return om.readValue(json, GeminiFeedbackForm.class);
        } catch (JsonProcessingException e) {

            throw new FeedbackParsingException();
        }
    }
    private GeminiResponse parsingGeminiResponse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<GeminiResponse.Question> questions = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(json);

            String content = root.path("content").asText();

            if (root.has("experienceExpansionQuestion")) {
                questions.add(new GeminiResponse.Question(
                        root.get("experienceExpansionQuestion").asText(),
                        "experienceExpansionQuestion"
                ));
            }

            if (root.has("valuesExplorationQuestion")) {
                questions.add(new GeminiResponse.Question(
                        root.get("valuesExplorationQuestion").asText(),
                        "valuesExplorationQuestion"
                ));
            }

            if (root.has("comparePastPresentQuestion")) {
                questions.add(new GeminiResponse.Question(
                        root.get("comparePastPresentQuestion").asText(),
                        "comparePastPresentQuestion"
                ));
            }

            return new GeminiResponse(content, questions);

        } catch (Exception e) {
            throw new FeedbackParsingException();
        }
    }

    private List<String> saveFeedbackQuestion(List<GeminiResponse.Question> questions, Feedback feedback) {
        List<String> feedbackQuestionContents = new ArrayList<>();
        for(GeminiResponse.Question question : questions){
            FeedbackQuestion newFeedbackQuestion = FeedbackQuestion.builder()
                    .questionType(question.getQuestionType())
                    .content(question.getContent())
                    .isActive(true)
                    .build();

            feedback.addFeedbackQuestion(newFeedbackQuestion);
            feedbackQuestionRepository.save(newFeedbackQuestion);
            feedbackQuestionContents.add(question.getContent());
        }
        return feedbackQuestionContents;
    }

}
