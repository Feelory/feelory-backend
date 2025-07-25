package com.feelory.feelory_backend.writings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    TODO. [TR-YOO] 로그인 기능 완성 후 userId는 Token에서 추출하기
*/
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWritingListRequest {
    private Long userId;
    private int year;
    private int month;
    private int page = 0 ;
    private int size = 10;
}
