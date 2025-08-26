package com.feelory.feelory_backend.domain.user.service;

import com.feelory.feelory_backend.domain.user.entity.User;
import com.feelory.feelory_backend.domain.user.entity.UserToken;
import com.feelory.feelory_backend.global.exception.exceptions.user.AlreadyDeletedUserException;
import com.feelory.feelory_backend.global.exception.exceptions.user.UserNotFoundException;
import com.feelory.feelory_backend.global.security.jwt.JwtProvider;
import com.feelory.feelory_backend.global.util.NicknameGenerator;
import com.feelory.feelory_backend.domain.user.dto.model.AuthProvider;
import com.feelory.feelory_backend.domain.user.dto.model.UserRole;
import com.feelory.feelory_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NicknameGenerator nicknameGenerator;
    private final JwtProvider jwtProvider;

    public User findOrCreateUser(String name, String phoneNumber, AuthProvider authProvider, Long providerUserId) {
        return userRepository.findByPhoneNumberAndIsActive(phoneNumber, true)
                .orElseGet(() -> userRepository.save(
                        createUser(name, phoneNumber, authProvider, providerUserId)
                ));
    }

    private User createUser(String userName, String phoneNumber, AuthProvider authProvider, Long providerUserId) {
        return User.builder()
                .name(userName)
                .nickname(nicknameGenerator.generate())
                .phoneNumber(phoneNumber)
                .role(UserRole.USER)
                .authProvider(authProvider)
                .providerUserId(providerUserId)
                .isActive(true)
                .build();
    }

    public boolean isActiveUser(Long userId) {
        return userRepository.existsByIdAndIsActiveTrue(userId);
    }

    @Transactional
    public void deleteUser() {
        Long userId = jwtProvider.getUserIdFromAuthentication();

        User user = getUser(userId);

        validateAlreadyDeletedUser(user);

        User deactivatedUser = deactivateUser(user);

        deactivateUserHasTokens(deactivatedUser);

        userRepository.save(deactivatedUser);
    }

    private void deactivateUserHasTokens(User deactivatedUser) {
        for(UserToken userToken : deactivatedUser.getUserTokens()){
            userToken.deactivate();
        }
    }

    private void validateAlreadyDeletedUser(User user) {
        if(!user.isActive()){
            throw new AlreadyDeletedUserException();
        }
    }

    private User deactivateUser(User user) {
        User.UserBuilder builder = user.toBuilder();
        builder.isActive(false);
        builder.role(UserRole.DEACTIVATED_USER);
        return builder.build();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
