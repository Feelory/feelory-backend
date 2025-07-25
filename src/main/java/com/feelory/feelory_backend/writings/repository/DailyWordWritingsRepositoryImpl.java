package com.feelory.feelory_backend.writings.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DailyWordWritingsRepositoryImpl implements DailyWordWritingsRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
}
