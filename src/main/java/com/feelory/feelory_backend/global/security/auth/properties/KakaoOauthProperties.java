package com.feelory.feelory_backend.global.security.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kakao")
@Getter
@Setter
public class KakaoOauthProperties {

    private String clientId;
    private String redirectUri;
    private String authUri;
}
