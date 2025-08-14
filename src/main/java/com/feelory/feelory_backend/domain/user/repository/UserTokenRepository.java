package com.feelory.feelory_backend.domain.user.repository;

import com.feelory.feelory_backend.domain.user.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByRefreshTokenAndIsActive(String refreshToken, boolean isActive);
}
