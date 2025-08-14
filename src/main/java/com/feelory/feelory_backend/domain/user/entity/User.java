package com.feelory.feelory_backend.domain.user.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import com.feelory.feelory_backend.domain.user.dto.model.AuthProvider;
import com.feelory.feelory_backend.domain.user.dto.model.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "nickname", nullable = false, length = 10)
    private String nickname;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "introduce", length = 200)
    private String introduce;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    private AuthProvider authProvider;

    @Column(name = "provider_user_id", nullable = false)
    private Long providerUserId;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserToken> userTokens = new ArrayList<>();

    public void addUserToken(UserToken token) {
        userTokens.add(token);
        token.setUser(this);
    }

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProfileImage> userProfileImages = new ArrayList<>();

    public void addUserProfileImage(UserProfileImage profileImage){
        userProfileImages.add(profileImage);
        profileImage.setUser(this);
    }
}
