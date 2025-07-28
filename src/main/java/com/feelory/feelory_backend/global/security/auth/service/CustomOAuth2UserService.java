package com.feelory.feelory_backend.global.security.auth.service;

import com.feelory.feelory_backend.global.exception.exceptions.users.InvalidPhoneNumberException;
import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.users.model.AuthProvider;
import com.feelory.feelory_backend.users.model.UserRole;
import com.feelory.feelory_backend.users.repository.UsersRepository;
import com.feelory.feelory_backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        Long kakaoId = ((Number) attributes.get("id")).longValue();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String name = (String) kakaoAccount.get("name");
        String phoneNumber = formatPhoneNumber((String) kakaoAccount.get("phone_number"));

        Users user = userService.findOrCreateUser(name, phoneNumber, AuthProvider.KAKAO, kakaoId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                attributes,
                "id"
        );
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new InvalidPhoneNumberException();
        }
        return phoneNumber.replace("+82", "0")
                .replaceAll("[^0-9]", "");
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
}