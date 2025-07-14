package com.feelory.feelory_backend.sample.contoller;

import com.feelory.feelory_backend.sample.service.SampleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
public class SampleController {

    @Value("${sample.hello}")
    private String STR_HELLO;

    SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("hello")
    public String sendHelloWorld() {

        return STR_HELLO;
    }

    @GetMapping("db-data")
    public String sendSampleTextFromDB() {

        return sampleService.getSampleTextFromDB();
    }
}
