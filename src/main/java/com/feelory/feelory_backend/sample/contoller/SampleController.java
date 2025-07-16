package com.feelory.feelory_backend.sample.contoller;

import com.feelory.feelory_backend.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sample")
public class SampleController {

    private final SampleService sampleService;

    @Value("${sample.hello}")
    private String STR_HELLO;

    @GetMapping("hello")
    public String sendHelloWorld() {

        return STR_HELLO;
    }

    @GetMapping("db-data")
    public String sendSampleTextFromDB() {

        return sampleService.getSampleTextFromDB();
    }

    @PostMapping("db-data")
    public String insertSampleText() {
        sampleService.insertSampleText();

        return "DB 데이터 저장 완료";
    }
}
