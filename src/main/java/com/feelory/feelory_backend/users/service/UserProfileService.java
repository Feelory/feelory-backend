package com.feelory.feelory_backend.users.service;

import com.feelory.feelory_backend.global.exception.exceptions.users.UserNotFoundException;
import com.feelory.feelory_backend.global.file.model.ImageFile;
import com.feelory.feelory_backend.global.file.service.FileUploadService;
import com.feelory.feelory_backend.global.security.auth.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.users.entity.UserProfileImages;
import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.users.model.response.UserProfileImageResponse;
import com.feelory.feelory_backend.users.repository.UserProfileImagesRepository;
import com.feelory.feelory_backend.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final JwtTokenProvider jwtTokenProvider;
    private final FileUploadService fileUploadService;
    private final UsersRepository usersRepository;
    private final UserProfileImagesRepository userProfileImagesRepository;

    @Value("${file.access.url.prefix}")
    private String prefix;

    @Transactional
    public UserProfileImageResponse updateProfileImage(MultipartFile requestImageFile) {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        ImageFile imageFile = fileUploadService.uploadImageFile(requestImageFile);

        Users user = getUsers(userId);

        deactivateCurrentProfileImages(user);

        UserProfileImages profileImage = buildUserProfileImages(user, imageFile);

        userProfileImagesRepository.save(profileImage);

        String imageFileUrl = imageFile.getImageFileUrl(prefix);

        return new UserProfileImageResponse(imageFileUrl);
    }

    private void deactivateCurrentProfileImages(Users user) {
        user.getUserProfileImages().stream()
                .filter(UserProfileImages::isActive)
                .forEach(UserProfileImages::deactivate);
    }

    private UserProfileImages buildUserProfileImages(Users user, ImageFile imageFile) {
        UserProfileImages image = UserProfileImages.builder()
                .imageName(imageFile.getImageName())
                .extension(imageFile.getExtension())
                .width(imageFile.getWidth())
                .height(imageFile.getHeight())
                .fileSize(imageFile.getFileSize())
                .isActive(true)
                .build();
        user.addUserProfileImage(image);
        return image;
    }

    private Users getUsers(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
