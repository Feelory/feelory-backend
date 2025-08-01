package com.feelory.feelory_backend.users.service;

import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.users.model.AuthProvider;
import com.feelory.feelory_backend.users.model.UserRole;
import com.feelory.feelory_backend.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public Users findOrCreateUser(String name, String phoneNumber, AuthProvider authProvider, Long providerUserId) {
        return usersRepository.findByPhoneNumberAndIsActive(phoneNumber, true)
                .orElseGet(() -> usersRepository.save(
                        createUsers(name, phoneNumber, authProvider, providerUserId)
                ));
    }

    private Users createUsers(String userName, String phoneNumber, AuthProvider authProvider, Long providerUserId) {
        return Users.builder()
                .name(userName)
                .nickname("임시닉네임" + (int) (Math.random() * 10000))
                .phoneNumber(phoneNumber)
                .role(UserRole.USER)
                .authProvider(authProvider)
                .providerUserId(providerUserId)
                .isActive(true)
                .build();
    }

    public boolean isActiveUser(Long userId) {
        return usersRepository.existsByIdAndIsActiveTrue(userId);
    }
}
