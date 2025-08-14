package com.feelory.feelory_backend.webclient.dto;

import java.util.List;

public class GenerateContentResponse {
    public List<Candidate> candidates;
//    public UsageMetadata usageMetadata;
//    public String modelVersion;
//    public String responseId;

    public static class Candidate {
        public Content content;
//        public String finishReason;
//        public int index;

        public static class Content {
            public List<Part> parts;
//            public String role;

            public static class Part {
                public String text;
            }
        }
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
}
