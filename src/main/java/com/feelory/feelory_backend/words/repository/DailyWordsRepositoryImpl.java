package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class DailyWordsRepositoryImpl implements DailyWordsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<DailyWords> findByTopicDateAndIsActive(LocalDateTime topicDate, Boolean isActive) {

        QDailyWords qDailyWords =  QDailyWords.dailyWords;

        LocalDateTime start = topicDate.toLocalDate().atStartOfDay();
        LocalDateTime end = topicDate.toLocalDate().atTime(23, 59, 59);

        DailyWords dailyWords = jpaQueryFactory
                        .selectFrom(qDailyWords)
                        .where(
                                qDailyWords.topicDate.between(start, end),
                                qDailyWords.isActive.eq(isActive)
                        )
                        .fetchOne();

        return Optional.ofNullable(dailyWords);
    }
}
