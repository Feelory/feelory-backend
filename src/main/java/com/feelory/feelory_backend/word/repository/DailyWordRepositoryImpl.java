package com.feelory.feelory_backend.word.repository;

import com.feelory.feelory_backend.word.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class DailyWordRepositoryImpl implements DailyWordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<DailyWord> findByTopicDateAndIsActive(LocalDateTime topicDate, Boolean isActive) {

        QDailyWord qDailyWord =  QDailyWord.dailyWord;
        QWord qWord = QWord.word;
        QWordCategory qWordCategory = QWordCategory.wordCategory;

        LocalDateTime start = topicDate.toLocalDate().atStartOfDay();
        LocalDateTime end = topicDate.toLocalDate().atTime(23, 59, 59);

        DailyWord dailyWord = jpaQueryFactory
                        .selectFrom(qDailyWord)
                        .leftJoin(qDailyWord.word, qWord).fetchJoin()
                        .leftJoin(qWord.category, qWordCategory).fetchJoin()
                        .where(
                                qDailyWord.topicDate.between(start, end),
                                qDailyWord.isActive.eq(isActive)
                        )
                        .fetchOne();

        return Optional.ofNullable(dailyWord);
    }
}
