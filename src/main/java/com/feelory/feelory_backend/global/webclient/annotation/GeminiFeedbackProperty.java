package com.feelory.feelory_backend.global.webclient.annotation;

import com.feelory.feelory_backend.global.webclient.dto.model.GeminiPropertyType;
import java.lang.annotation.*;

/*
    Gemini responseSchema/응답 JSON 생성을 위한 필드 메타데이터.
*/

@Retention(RetentionPolicy.RUNTIME)         // 리플렉션 통해서 메타데이터 추출하기 위해 사용
@Target(ElementType.FIELD)
public @interface GeminiFeedbackProperty {
    GeminiPropertyType type();              // 프로퍼티 자료형
    String description() default "";        // 필드 설명(LLM 안내용, 설정용)
    int order() default Integer.MAX_VALUE;  // 출력 순서(작을수록 먼저)
    boolean required() default true;        // 필수 필드 여부
    String[] enumValues() default {};       // 사용할 ENUM 목록 (주로 GeminiPropertyType.STRING 일 때 사용 가능)
    String name() default "";               // JSON 키(미지정 시 필드명 사용)
}
