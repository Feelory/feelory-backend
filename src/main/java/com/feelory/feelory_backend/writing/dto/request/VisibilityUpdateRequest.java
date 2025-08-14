package com.feelory.feelory_backend.writing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisibilityUpdateRequest {
    @NotNull(message = "id 값은 필수입니다")
    private Long id;
    @NotNull(message = "visibility 값은 필수입니다")
    private Boolean visibility;
}
