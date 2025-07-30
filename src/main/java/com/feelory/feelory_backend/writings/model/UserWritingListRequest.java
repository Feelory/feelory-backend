package com.feelory.feelory_backend.writings.model;

import lombok.*;

/*
    TODO. [TR-YOO] 로그인 기능 완성 후 userId는 Token에서 추출하기
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWritingListRequest {
    private Long userId;
    private Integer year;
    private Integer month;
    private int page = 0 ;
    private int size = 10;
}
