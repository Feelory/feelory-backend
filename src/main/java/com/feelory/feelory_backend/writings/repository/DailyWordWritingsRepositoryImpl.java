package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.words.entity.QDailyWords;
import com.feelory.feelory_backend.words.entity.QWordCategories;
import com.feelory.feelory_backend.words.entity.QWords;
import com.feelory.feelory_backend.writings.model.WritingSearchDto;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import com.feelory.feelory_backend.writings.entity.QDailyWordWritings;
import com.feelory.feelory_backend.writings.entity.QWritingGoals;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
    TODO. [TR-YOO] userId -> user객체로 추후 변경하기
*/
@RequiredArgsConstructor
public class DailyWordWritingsRepositoryImpl implements DailyWordWritingsRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DailyWordWritings> searchWritings(WritingSearchDto dto, Pageable pageable) {
        QDailyWordWritings qDailyWordWritings = QDailyWordWritings.dailyWordWritings;
        QDailyWords qDailyWords = QDailyWords.dailyWords;
        QWords qWords = QWords.words;
        QWordCategories qWordCategories = QWordCategories.wordCategories;
        QWritingGoals qWritingGoals = QWritingGoals.writingGoals;

        BooleanBuilder builder = new BooleanBuilder();


        if(dto.getUserId() != null) {
            builder.and(qDailyWordWritings.userId.eq(dto.getUserId()));
        }

        if(dto.getSearchDate() != null) {
            LocalDateTime start = dto.getSearchDate().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime end = start.plusMonths(1).minusNanos(1);

            builder.and(qDailyWordWritings.createdAt.between(start, end));
        }

        if(dto.getIsActive() != null) {
            builder.and(qDailyWordWritings.isActive.eq(dto.getIsActive()));
        }

        List<DailyWordWritings> content = jpaQueryFactory
                .selectFrom(qDailyWordWritings)
                .join(qDailyWordWritings.dailyWord, qDailyWords).fetchJoin()
                .join(qDailyWords.word, qWords).fetchJoin()
                .join(qWords.category, qWordCategories).fetchJoin()
                .join(qDailyWordWritings.writingGoal, qWritingGoals).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(qDailyWordWritings.count())
                        .from(qDailyWordWritings)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<DailyWordWritings> searchWritingDetailByDto(WritingSearchDto writingSearchDto) {
        QDailyWordWritings qDailyWordWritings = QDailyWordWritings.dailyWordWritings;
        QDailyWords qDailyWords = QDailyWords.dailyWords;
        QWords qWords = QWords.words;
        QWordCategories qWordCategories = QWordCategories.wordCategories;
        QWritingGoals qWritingGoals = QWritingGoals.writingGoals;

        LocalDateTime searchDate = writingSearchDto.getSearchDate();
        LocalDateTime start = searchDate.toLocalDate().atStartOfDay();
        LocalDateTime end = searchDate.toLocalDate().atTime(23, 59, 59);

        DailyWordWritings content = jpaQueryFactory
                .selectFrom(qDailyWordWritings)
                .join(qDailyWordWritings.dailyWord, qDailyWords).fetchJoin()
                .join(qDailyWords.word, qWords).fetchJoin()
                .join(qWords.category, qWordCategories).fetchJoin()
                .join(qDailyWordWritings.writingGoal, qWritingGoals).fetchJoin()
                .where(
                    qDailyWordWritings.userId.eq(writingSearchDto.getUserId()),
                    qDailyWordWritings.createdAt.between(start, end),
                    qDailyWordWritings.isActive.eq(writingSearchDto.getIsActive())
                )
                .fetchOne();

        return Optional.ofNullable(content);
    }
}
