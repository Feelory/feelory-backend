package com.feelory.feelory_backend.sample.contoller;

import com.feelory.feelory_backend.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sample")
@Tag(name = "샘플", description = "샘플 API 목록 (테스트용)")
public class SampleController {

    private final SampleService sampleService;

    @Value("${sample.hello}")
    private String STR_HELLO;

    @Operation(
            summary = "Hello World 테스트",
            description = "String 출력 + jasypt 적용 테스트 API"
    )
    @GetMapping("hello")
    public String sendHelloWorld() {

        return STR_HELLO;
    }

    @Operation(
            summary = "DB 조회 테스트",
            description = "DB 데이터 조회 테스트 API"
    )
    @GetMapping("db-data")
    public String sendSampleTextFromDB() {

        return sampleService.getSampleTextFromDB();
    }

    @Operation(
            summary = "DB 저장 테스트",
            description = "DB 데이터 저장 테스트 API"
    )
    @PostMapping("db-data")
    public String insertSampleText() {
        sampleService.insertSampleText();

        return "DB 데이터 저장 완료";
    }

    @Operation(
            summary = "BaseException 테스트",
            description = "BaseException 테스트 API"
    )
    @GetMapping("throw-base-exception")
    public String throwBaseException() {
        sampleService.throwBaseException();
        return "이 메시지는 절대 도달하지 않습니다.";
    }

    @Operation(
            summary = "UnknownException 테스트",
            description = "UnknownException 테스트 API"
    )
    @GetMapping("throw-unknown-exception")
    public String throwUnknownException() {
        sampleService.throwUnknownException();
        return "이 메시지도 도달하지 않습니다.";
    }
}
