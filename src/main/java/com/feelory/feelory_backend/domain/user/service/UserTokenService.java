    package com.feelory.feelory_backend.domain.user.service;

    import com.feelory.feelory_backend.domain.user.entity.User;
    import com.feelory.feelory_backend.global.exception.exceptions.auth.ExpiredRefreshTokenException;
    import com.feelory.feelory_backend.global.exception.exceptions.auth.RefreshTokenNotFoundException;
    import com.feelory.feelory_backend.domain.user.entity.UserToken;
    import com.feelory.feelory_backend.domain.user.repository.UserTokenRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;

    @Service
    @RequiredArgsConstructor
    public class UserTokenService {

        private final UserTokenRepository userTokenRepository;

        public UserToken saveUserToken(User user, String refreshToken, LocalDateTime refreshTokenExp) {
            UserToken token = UserToken.builder()
                    .refreshToken(refreshToken)
                    .refreshTokenExp(refreshTokenExp)
                    .isActive(true)
                    .build();
            user.addUserToken(token);
            return userTokenRepository.save(token);
        }

        public UserToken findRefreshToken(String oldRefreshToken) {
            return userTokenRepository.findByRefreshTokenAndIsActive(oldRefreshToken,true)
                    .orElseThrow(RefreshTokenNotFoundException::new);
        }

        public void rotateRefreshToken(UserToken oldToken, String newRefreshToken, LocalDateTime newRefreshTokenExp) {

            if (oldToken.getRefreshTokenExp().isBefore(LocalDateTime.now())) {
                throw new ExpiredRefreshTokenException();
            }

            User user = oldToken.getUser();

            oldToken.deactivate();
            userTokenRepository.save(oldToken);

            UserToken newToken = UserToken.builder()
                    .refreshToken(newRefreshToken)
                    .refreshTokenExp(newRefreshTokenExp)
                    .isActive(true)
                    .build();

            user.addUserToken(newToken);

            userTokenRepository.save(newToken);
        }

        public void deactivateRefreshToken(UserToken refreshToken) {
            refreshToken.deactivate();
        }
    }
