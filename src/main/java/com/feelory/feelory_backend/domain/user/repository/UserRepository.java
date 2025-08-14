package com.feelory.feelory_backend.domain.user.repository;

import com.feelory.feelory_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumberAndIsActive(String phoneNumber, boolean isActive);

    boolean existsByIdAndIsActiveTrue(Long userId);
}
