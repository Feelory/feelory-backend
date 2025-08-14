package com.feelory.feelory_backend.domain.user.service;

import com.feelory.feelory_backend.domain.user.entity.User;
import com.feelory.feelory_backend.global.util.NicknameGenerator;
import com.feelory.feelory_backend.domain.user.dto.model.AuthProvider;
import com.feelory.feelory_backend.domain.user.dto.model.UserRole;
import com.feelory.feelory_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NicknameGenerator nicknameGenerator;

    public User findOrCreateUser(String name, String phoneNumber, AuthProvider authProvider, Long providerUserId) {
        return userRepository.findByPhoneNumberAndIsActive(phoneNumber, true)
                .orElseGet(() -> userRepository.save(
                        createUsers(name, phoneNumber, authProvider, providerUserId)
                ));
    }

    private User createUsers(String userName, String phoneNumber, AuthProvider authProvider, Long providerUserId) {
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
}
