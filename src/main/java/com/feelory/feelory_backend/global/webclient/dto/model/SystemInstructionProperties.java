package com.feelory.feelory_backend.global.webclient.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "feedback.system-instruction")
public class SystemInstructionProperties {
    private List<String> prompts = new ArrayList<>();
}
