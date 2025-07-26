package com.feelory.feelory_backend.users.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "user_tokens")
public class UserTokens extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "refresh_token_exp", nullable = false)
    private LocalDateTime refreshTokenExp;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public void setUser(Users users) {
        this.user = users;
        users.addUserToken(this);
    }
}
