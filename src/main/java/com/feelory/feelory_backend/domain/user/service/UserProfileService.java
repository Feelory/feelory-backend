package com.feelory.feelory_backend.domain.user.service;

import com.feelory.feelory_backend.global.exception.exceptions.users.UserNotFoundException;
import com.feelory.feelory_backend.global.file.model.ImageFile;
import com.feelory.feelory_backend.global.file.service.FileUploadService;
import com.feelory.feelory_backend.global.security.jwt.JwtTokenProvider;
import com.feelory.feelory_backend.domain.user.entity.UserProfileImages;
import com.feelory.feelory_backend.domain.user.entity.Users;
import com.feelory.feelory_backend.domain.user.dto.response.UserProfileImageResponse;
import com.feelory.feelory_backend.domain.user.dto.response.UserProfileResponse;
import com.feelory.feelory_backend.domain.user.repository.UserProfileImagesRepository;
import com.feelory.feelory_backend.domain.user.repository.UsersRepository;
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

        String imageFileUrl = getProfileImageUrl(profileImage);

        return new UserProfileImageResponse(imageFileUrl);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse readUserProfile() {

        Long userId = jwtTokenProvider.getUserIdFromAuthentication();

        Users user = getUsers(userId);

        UserProfileImages activeProfileImage = getActiveProfileImageFromUser(user);

        String profileImageUrl = getProfileImageUrl(activeProfileImage);

        return buildUserProfileResponse(user,profileImageUrl);
    }

    private UserProfileResponse buildUserProfileResponse(Users user,String profileImageUrl) {
        return UserProfileResponse.builder()
                .profileImageUrl(profileImageUrl)
                .nickname(user.getNickname())
                .introduce(user.getIntroduce())
                .build();
    }

    private UserProfileImages getActiveProfileImageFromUser(Users user) {
        return user.getUserProfileImages().stream()
                .filter(UserProfileImages::isActive)
                .findFirst()
                .orElse(null);
    }

    private String getProfileImageUrl(UserProfileImages profileImage) {
        if (profileImage == null) {
            return null;
        }
        return prefix + "/" + profileImage.getImageName() + "." + profileImage.getExtension();
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
