package com.feelory.feelory_backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateRequest {
    @NotBlank(message = "닉네임 값은 필수 입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2글자 이상, 10글자 이하이어야 합니다.")
    private String nickname;

    @Size(max = 200, message = "자기소개는 최대 200자 까지 입력 가능합니다.")
    private String introduce;
}
