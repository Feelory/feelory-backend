package com.feelory.feelory_backend.users.service;

import com.feelory.feelory_backend.users.entity.UserTokens;
import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.users.repository.UserTokensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    private final UserTokensRepository userTokensRepository;

    public UserTokens saveUserToken(Users user, String refreshToken, LocalDateTime refreshTokenExp) {
        UserTokens token = UserTokens.builder()
                .refreshToken(refreshToken)
                .refreshTokenExp(refreshTokenExp)
                .isActive(true)
                .build();
        user.addUserToken(token);
        return userTokensRepository.save(token);
    }
}
