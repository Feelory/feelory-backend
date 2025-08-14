package com.feelory.feelory_backend.domain.writing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
public class UserWritingDeleteRequest {
    @NotNull(message = "id 값은 필수입니다.")
    private Long id;
}
