package com.feelory.feelory_backend.domain.user.repository;

import com.feelory.feelory_backend.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPhoneNumberAndIsActive(String phoneNumber, boolean isActive);

    boolean existsByIdAndIsActiveTrue(Long userId);
}
