package com.feelory.feelory_backend.writings.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/*
    TODO. [TR-YOO] 로그인 기능 완성되면 추후 해당 객체 삭제
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTodayWritingRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;
}
