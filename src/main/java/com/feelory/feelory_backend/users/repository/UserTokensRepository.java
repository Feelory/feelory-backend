package com.feelory.feelory_backend.users.repository;

import com.feelory.feelory_backend.users.entity.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {
    Optional<UserTokens> findByRefreshTokenAndIsActive(String refreshToken, boolean isActive);
}
