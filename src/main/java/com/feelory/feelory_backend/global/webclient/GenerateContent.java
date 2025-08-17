package com.feelory.feelory_backend.global.webclient;

import com.feelory.feelory_backend.global.exception.ExceptionFileLogger;
import com.feelory.feelory_backend.global.exception.exceptions.feedback.ExternalApiConnectionException;
import com.feelory.feelory_backend.global.exception.exceptions.feedback.GenerateContentFailedException;
import com.feelory.feelory_backend.global.webclient.dto.request.GenerateContentRequest;
import com.feelory.feelory_backend.global.webclient.dto.reponse.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenerateContent {

    private final WebClient geminiWebClient;
    private final ExceptionFileLogger exceptionFileLogger;

    public GenerateContentResponse generate(GenerateContentRequest request) {
        try {
            return geminiWebClient.post()
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GenerateContentResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            exceptionFileLogger.writeToFile(e);
            log.error("Gemini API 호출 중 오류 발생. 상태 코드: {}, 응답 내용: {}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new GenerateContentFailedException();
        } catch (WebClientRequestException e) {
            exceptionFileLogger.writeToFile(e);
            log.error("Gemini API 서버 연결 실패. 메시지: {}", e.getMessage(), e);
            throw new ExternalApiConnectionException();
        }
    }
}
