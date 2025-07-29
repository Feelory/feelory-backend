    package com.feelory.feelory_backend.users.service;

    import com.feelory.feelory_backend.global.exception.exceptions.auth.ExpiredRefreshTokenException;
    import com.feelory.feelory_backend.global.exception.exceptions.auth.RefreshTokenNotFoundException;
    import com.feelory.feelory_backend.users.entity.UserTokens;
    import com.feelory.feelory_backend.users.entity.Users;
    import com.feelory.feelory_backend.users.repository.UserTokensRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;

    @Service
    @RequiredArgsConstructor
    public class UserTokenService {

        private final UserTokensRepository userTokensRepository;

        public UserTokens saveUserToken(Users user, String refreshToken, LocalDateTime refreshTokenExp) {
            UserTokens token = UserTokens.builder()
                    .refreshToken(refreshToken)
                    .refreshTokenExp(refreshTokenExp)
                    .isActive(true)
                    .build();
            user.addUserToken(token);
            return userTokensRepository.save(token);
        }

        public UserTokens findRefreshToken(String oldRefreshToken) {
            return userTokensRepository.findByRefreshTokenAndIsActive(oldRefreshToken,true)
                    .orElseThrow(RefreshTokenNotFoundException::new);
        }

        public void rotateRefreshToken(UserTokens oldToken, String newRefreshToken, LocalDateTime newRefreshTokenExp) {

            if (oldToken.getRefreshTokenExp().isBefore(LocalDateTime.now())) {
                throw new ExpiredRefreshTokenException();
            }

            Users user = oldToken.getUser();

            oldToken.deactivate();
            userTokensRepository.save(oldToken);

            UserTokens newToken = UserTokens.builder()
                    .refreshToken(newRefreshToken)
                    .refreshTokenExp(newRefreshTokenExp)
                    .isActive(true)
                    .build();

            user.addUserToken(newToken);

            userTokensRepository.save(newToken);
        }
    }
