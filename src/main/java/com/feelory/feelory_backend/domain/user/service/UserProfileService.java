package com.feelory.feelory_backend.domain.user.service;

import com.feelory.feelory_backend.domain.user.entity.User;
import com.feelory.feelory_backend.domain.user.entity.UserProfileImage;
import com.feelory.feelory_backend.global.exception.exceptions.user.UserNotFoundException;
import com.feelory.feelory_backend.global.file.model.ImageFile;
import com.feelory.feelory_backend.global.file.service.FileUploadService;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
import com.feelory.feelory_backend.domain.user.dto.response.UserProfileImageResponse;
import com.feelory.feelory_backend.domain.user.dto.response.UserProfileResponse;
import com.feelory.feelory_backend.domain.user.repository.UserProfileImageRepository;
import com.feelory.feelory_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final JwtProvider jwtProvider;
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;

    @Value("${file.access.url.prefix}")
    private String prefix;

    @Transactional
    public UserProfileImageResponse updateProfileImage(MultipartFile requestImageFile) {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        ImageFile imageFile = fileUploadService.uploadImageFile(requestImageFile);

        User user = getUsers(userId);

        deactivateCurrentProfileImages(user);

        UserProfileImage profileImage = buildUserProfileImages(user, imageFile);

        userProfileImageRepository.save(profileImage);

        String imageFileUrl = getProfileImageUrl(profileImage);

        return new UserProfileImageResponse(imageFileUrl);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse readUserProfile() {

        Long userId = jwtProvider.getUserIdFromAuthentication();

        User user = getUsers(userId);

        UserProfileImage activeProfileImage = getActiveProfileImageFromUser(user);

        String profileImageUrl = getProfileImageUrl(activeProfileImage);

        return buildUserProfileResponse(user,profileImageUrl);
    }

    private UserProfileResponse buildUserProfileResponse(User user, String profileImageUrl) {
        return UserProfileResponse.builder()
                .profileImageUrl(profileImageUrl)
                .nickname(user.getNickname())
                .introduce(user.getIntroduce())
                .build();
    }

    private UserProfileImage getActiveProfileImageFromUser(User user) {
        return user.getUserProfileImages().stream()
                .filter(UserProfileImage::isActive)
                .findFirst()
                .orElse(null);
    }

    private String getProfileImageUrl(UserProfileImage profileImage) {
        if (profileImage == null) {
            return null;
        }
        return prefix + "/" + profileImage.getImageName() + "." + profileImage.getExtension();
    }

    private void deactivateCurrentProfileImages(User user) {
        user.getUserProfileImages().stream()
                .filter(UserProfileImage::isActive)
                .forEach(UserProfileImage::deactivate);
    }

    private UserProfileImage buildUserProfileImages(User user, ImageFile imageFile) {
        UserProfileImage image = UserProfileImage.builder()
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

    private User getUsers(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
