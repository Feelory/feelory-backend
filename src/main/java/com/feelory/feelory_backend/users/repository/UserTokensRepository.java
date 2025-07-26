package com.feelory.feelory_backend.users.repository;

import com.feelory.feelory_backend.users.entity.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {

}
