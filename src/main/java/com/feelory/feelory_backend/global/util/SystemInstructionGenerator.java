package com.feelory.feelory_backend.global.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SystemInstructionGenerator {

    private final List<String> prompt = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource("trait_animal_nickname.csv");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                boolean isFirst = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirst) {
                        isFirst = false;
                        continue;
                    }

                    prompt.add(line);
                }
            }
        } catch (Exception e) {
            log.error("Prompt CSV 로딩 실패", e);
        }
    }

    public List<String> getPrompts() {

        return this.prompt;
    }
}
