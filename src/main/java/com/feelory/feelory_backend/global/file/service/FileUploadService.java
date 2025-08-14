package com.feelory.feelory_backend.global.file.service;

import com.feelory.feelory_backend.global.exception.exceptions.file.FileSaveFailedException;
import com.feelory.feelory_backend.global.exception.exceptions.file.InvalidFileNameException;
import com.feelory.feelory_backend.global.exception.exceptions.file.FileNotProvidedException;
import com.feelory.feelory_backend.global.exception.exceptions.file.ImageFileTooLargeException;
import com.feelory.feelory_backend.global.exception.exceptions.file.UnsupportedImageFormatException;
import com.feelory.feelory_backend.global.file.model.ImageFileDto;
import com.feelory.feelory_backend.global.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private static final long MAX_IMAGE_FILE_SIZE = 15 * 1024 * 1024;
    private static final Set<String> ALLOWED_IMAGE_FILE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    private final ValidationUtil validationUtil;

    @Value("${file.upload.path}")
    private String uploadPath;

    public ImageFileDto uploadImageFile(MultipartFile requestImageFile) {
        try {
            validateImageFile(requestImageFile);

            createUploadDirIfNotExists();

            String newImageFileName = String.valueOf(UUID.randomUUID());
            String extension = extractExtension(requestImageFile.getOriginalFilename());

            saveFileToLocal(requestImageFile, newImageFileName, extension);
            int[] dimensions = extractImageDimensions(requestImageFile);

            return buildImageFile(newImageFileName, extension, requestImageFile.getSize(), dimensions[0], dimensions[1]);
        } catch (IOException e) {
            log.error("Error occurred while uploading file: {}", e.getMessage(), e);
            throw new FileSaveFailedException();
        }
    }

    private void validateImageFile(MultipartFile imageFile) {
        validateFileProvided(imageFile);
        validateFileName(imageFile);
        validateAllowedFileExtension(imageFile);
        validateFileSizeWithinLimit(imageFile);
    }

    private void validateFileProvided(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new FileNotProvidedException();
        }
    }

    private void validateFileName(MultipartFile imageFile) {
        String imageFileName = imageFile.getOriginalFilename();
        if (!validationUtil.hasText(imageFileName)) {
            throw new InvalidFileNameException();
        }
        if (!imageFileName.contains(".") || imageFileName.lastIndexOf('.') == imageFileName.length() - 1) {
            throw new UnsupportedImageFormatException();
        }
    }

    private void validateAllowedFileExtension(MultipartFile imageFile) {
        String imageFileName = imageFile.getOriginalFilename();
        if (!ALLOWED_IMAGE_FILE_EXTENSIONS.contains(extractExtension(imageFileName))) {
            throw new UnsupportedImageFormatException();
        }
    }

    private void validateFileSizeWithinLimit(MultipartFile imageFile) {
        if (imageFile.getSize() > MAX_IMAGE_FILE_SIZE) {
            throw new ImageFileTooLargeException();
        }
    }

    private void createUploadDirIfNotExists() throws IOException {
        Path dirPath = Paths.get(uploadPath);
        Files.createDirectories(dirPath);
    }

    private void saveFileToLocal(MultipartFile file, String newFileName, String extension) throws IOException {
        Path targetLocation = Paths.get(uploadPath).resolve(newFileName+"."+extension);
        Files.copy(file.getInputStream(), targetLocation);
    }

    private int[] extractImageDimensions(MultipartFile imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile.getInputStream());
        if (image == null) {
            return new int[]{0, 0};
        }
        return new int[]{image.getWidth(), image.getHeight()};
    }

    private ImageFileDto buildImageFile(String newFileName, String extension, long fileSize, int width, int height) {
        return ImageFileDto.builder()
                .imageName(newFileName)
                .extension(extension)
                .fileSize(fileSize)
                .width(width)
                .height(height)
                .build();
    }

    private String extractExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
}