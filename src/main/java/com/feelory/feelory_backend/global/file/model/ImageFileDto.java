package com.feelory.feelory_backend.global.file.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileDto {
    private String imageName;
    private String extension;
    private int width;
    private int height;
    private Long fileSize;
}
