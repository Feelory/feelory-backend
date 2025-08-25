package com.feelory.feelory_backend.global.webclient.dto.reponse;

import com.feelory.feelory_backend.global.webclient.dto.model.GeminiBlockReason;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiFinishReason;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiHarmCategory;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiHarmProbability;

import java.util.List;

public class GenerateContentResponse {
    public List<Candidate> candidates;
//    public UsageMetadata usageMetadata;
    public PromptFeedback promptFeedback;               // 프롬프트 전체에 대한 안전 등급
    public String modelVersion;                         // 로깅 목적의 사용한 모델 버전
    public String responseId;                           // 로깅 목적의 LLM 응답의 고유 ID (지원팀 제공용)

    public static class Candidate {
        public Content content;
        public GeminiFinishReason finishReason;             // 토큰 생성 중단 사유
        public List<SafetyRating> safetyRatings;            // 응답 후보군 별 안전 등급
        public Integer tokenCount;                          // 해당 후보의 토큰 수
//        public int index;

        public static class Content {
            public List<Part> parts;
//            public String role;

            public static class Part {
                public String text;
            }
        }
    }

    public static class PromptFeedback {
        public GeminiBlockReason blockReason;           // 프롬프트가 차단되었을 시 사유
        public List<SafetyRating> safetyRatings;
    }


//    public static class UsageMetadata {
//        public int promptTokenCount;
//        public int candidatesTokenCount;
//        public int totalTokenCount;
//        public List<PromptTokensDetail> promptTokensDetails;
//
//        public static class PromptTokensDetail {
//            public String modality;
//            public int tokenCount;
//        }
//
//        public Integer thoughtsTokenCount;
//    }

    public static class SafetyRating {
        public GeminiHarmCategory category;         // 유해 콘텐츠 유형
        public GeminiHarmProbability probability;   // 해당 카테고리 기준으로 유해 콘텐츠일 확률
        public boolean blocked;                     // 해당 값으로 응답 필터링 여부를 확인할 수 있습니다.
                                                    // 후보군 중 blocked가 있는 항목을 제외하고 응답을 선택하는 식으로 활용할 수 있습니다.
    }

}
