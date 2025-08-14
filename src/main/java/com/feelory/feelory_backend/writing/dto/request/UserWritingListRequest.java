package com.feelory.feelory_backend.writing.dto.request;

import lombok.*;

import java.time.LocalDateTime;

/*
    TODO. [TR-YOO] 로그인 기능 완성 후 userId는 Token에서 추출하기
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWritingListRequest {
    private Integer year;
    private Integer month;
    private int page = 0 ;
    private int size = 10;

    public LocalDateTime getSearchDate() {

        return year != null && month != null ? LocalDateTime.of(year, month, 1, 0, 0) : null;
    }
}
