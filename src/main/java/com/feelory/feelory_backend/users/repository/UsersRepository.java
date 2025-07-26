package com.feelory.feelory_backend.users.repository;

import com.feelory.feelory_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPhoneNumberAndIsActive(String phoneNumber, boolean isActive);
}
