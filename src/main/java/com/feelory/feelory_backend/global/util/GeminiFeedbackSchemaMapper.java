package com.feelory.feelory_backend.global.util;

import com.feelory.feelory_backend.global.webclient.dto.model.GeminiFeedbackForm;
import com.feelory.feelory_backend.global.webclient.annotation.GeminiFeedbackProperty;
import com.feelory.feelory_backend.global.webclient.dto.model.GeminiResponseType;
import com.feelory.feelory_backend.global.webclient.dto.request.GenerateContentRequest;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.*;

@NoArgsConstructor
public class GeminiFeedbackSchemaMapper {

    // 외부 공개: 캐시된 스키마만 반환
    public static GenerateContentRequest.Schema schema() {
        return Holder.SCHEMA;
    }

    // 실제 빌드는 한 번만
    private static GenerateContentRequest.Schema buildOnce() {

        List<Field> annotated = collectAnnotatedFields(GeminiFeedbackForm.class);

        sortByOrder(annotated);

        BuildContext ctx = buildProperties(annotated);

        return buildSchema(ctx);
    }

    // --- 동작 분리 ---

    // 어노테이션이 붙은 필드 추출
    private static List<Field> collectAnnotatedFields(Class<?> target) {
        Field[] fields = target.getDeclaredFields();
        List<Field> annotated = new ArrayList<>();

        for (Field f : fields) {

            // isSynthetic => 컴파일러가 내부용으로 만든 어노테이션인지 여부
            // isAnnotationPresent => 해당 필드에 GeminiFeedbackProperty 어노테이션이 붙어있는지 여부

            if (!f.isSynthetic() && f.isAnnotationPresent(GeminiFeedbackProperty.class)) {
                annotated.add(f);
            }
        }

        return annotated;
    }

    // order에 따른 순서 정렬
    private static void sortByOrder(List<Field> fields) {
        fields.sort(Comparator.comparingInt(f ->
                f.getAnnotation(GeminiFeedbackProperty.class).order()));
    }

    // 중간 산출물 생성
    private static BuildContext buildProperties(List<Field> fields) {
        // 필드를 삽입한 순서를 기억해야하므로 LinkedHashMap 사용
        Map<String, GenerateContentRequest.Property> properties = new LinkedHashMap<>();
        List<String> ordering = new ArrayList<>();
        List<String> required = new ArrayList<>();

        for (Field f : fields) {
            GeminiFeedbackProperty meta = f.getAnnotation(GeminiFeedbackProperty.class);
            String key = meta.name().isEmpty() ? f.getName() : meta.name();

            properties.put(key, toProperty(meta));
            ordering.add(key);
            if (meta.required()) required.add(key);
        }
        return new BuildContext(properties, ordering, required);
    }

    // GenerateContentRequest.Property 객체로 변환
    private static GenerateContentRequest.Property toProperty(GeminiFeedbackProperty meta) {
        List<String> enums = meta.enumValues().length == 0
                ? null
                : Arrays.asList(meta.enumValues());
        return new GenerateContentRequest.Property(meta.type(), meta.description(), enums);
    }

    // 최종 산출물로 변환
    private static GenerateContentRequest.Schema buildSchema(BuildContext ctx) {

        /*
            - 현재 Schema 생성 방식 -> 캐싱을 통한 하나의 Schema를 공유하고 있음 (메모리 효율성을 위하여)
            - 여러 쓰레드가 공유하게 되므로 한쪽에서 임의적으로 변경하지 못하도록 불변으로 return 해야 함
            - unmodifiableMap()은 불변 뷰를 return함
            - 뷰를 대상으로 put, remove 등의 수정은 불가능함
        */
        Map<String, GenerateContentRequest.Property> propsFinal = Collections.unmodifiableMap(ctx.properties);

        List<String> orderingFinal = ctx.ordering.isEmpty() ? null : List.copyOf(ctx.ordering);
        List<String> requiredFinal = ctx.required.isEmpty() ? null : List.copyOf(ctx.required);

        return new GenerateContentRequest.Schema(
                GeminiResponseType.OBJECT,
                false,
                propsFinal,
                orderingFinal,
                requiredFinal
        );
    }

    // 중간 산출물 컨텍스트
    private static final class BuildContext {
        final Map<String, GenerateContentRequest.Property> properties;
        final List<String> ordering;
        final List<String> required;

        BuildContext(Map<String, GenerateContentRequest.Property> properties,
                     List<String> ordering,
                     List<String> required) {
            this.properties = properties;
            this.ordering = ordering;
            this.required = required;
        }
    }

    // 지연 초기화 + 스레드 안전 캐시
    private static final class Holder {
        private static final GenerateContentRequest.Schema SCHEMA = buildOnce();
    }
}
