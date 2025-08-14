package com.feelory.feelory_backend.global.webclient.dto.request;

import lombok.*;

import java.util.List;

/**
 * 예시 JSON
 * {
 *   "contents": [
 *     {
 *       "parts": [
 *         {
 *           "text": "민트초코와 가지무침의 공통점과 차이점"
 *         }
 *       ]
 *     }
 *   ]
 * }
 *
 * 매핑 구조
 * RequestDto
 *  └── contents : List<Content>
 *        └── parts : List<Part>
 *              └── text : String
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateContentRequest {
    private List<Content> contents;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {
        private String text;
    }

    public static GenerateContentRequest ofText(String text) {
        Part part = new Part(text);
        Content content = new Content(List.of(part));
        return new GenerateContentRequest(List.of(content));
    }
}


