package com.feelory.feelory_backend.global.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class NicknameGenerator {

    private final List<String> traits = new ArrayList<>();
    private final List<String> animals = new ArrayList<>();
    private final Random random = new Random();

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
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        traits.add(parts[0].trim());
                        animals.add(parts[1].trim());
                    }
                }
            }
        } catch (Exception e) {
            log.error("닉네임 CSV 로딩 실패", e);
        }
    }

    public String generate() {
        String trait = traits.get(random.nextInt(traits.size()));
        String animal = animals.get(random.nextInt(animals.size()));
        return trait + " " + animal;
    }
}

