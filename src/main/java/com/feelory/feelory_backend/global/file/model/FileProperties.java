package com.feelory.feelory_backend.global.file.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private Upload upload;
    private Access access;

    @Getter
    @Setter
    public static class Upload {
        private String path;
    }

    @Getter
    @Setter
    public static class Access {
        private String urlPrefix;
    }
}
