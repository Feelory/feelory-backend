package com.feelory.feelory_backend.global.file;

import com.feelory.feelory_backend.global.file.model.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class FileConfig implements WebMvcConfigurer {

    private final FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(fileProperties.getUpload().getPath()).toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/files/**")
                .addResourceLocations(absolutePath);
    }
}
