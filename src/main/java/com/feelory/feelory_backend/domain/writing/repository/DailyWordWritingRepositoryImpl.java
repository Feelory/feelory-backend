package com.feelory.feelory_backend.domain.writing.repository;

import com.feelory.feelory_backend.domain.user.entity.QUser;
import com.feelory.feelory_backend.domain.word.entity.QDailyWord;
import com.feelory.feelory_backend.domain.word.entity.QWordCategory;
import com.feelory.feelory_backend.domain.word.entity.QWord;
import com.feelory.feelory_backend.domain.writing.dto.model.WritingSearchDto;
import com.feelory.feelory_backend.domain.writing.entity.DailyWordWriting;
import com.feelory.feelory_backend.domain.writing.entity.QDailyWordWriting;
import com.feelory.feelory_backend.domain.writing.entity.QWritingGoal;
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
public class DailyWordWritingRepositoryImpl implements DailyWordWritingRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<DailyWordWriting> searchWritings(WritingSearchDto dto, Pageable pageable) {
        QDailyWordWriting qDailyWordWriting = QDailyWordWriting.dailyWordWriting;
        QDailyWord qDailyWord = QDailyWord.dailyWord;
        QUser qUsers = QUser.user;
        QWord qWord = QWord.word;
        QWordCategory qWordCategory = QWordCategory.wordCategory;
        QWritingGoal qWritingGoal = QWritingGoal.writingGoal;

        BooleanBuilder builder = new BooleanBuilder();


        if(dto.getUserId() != null) {
            builder.and(qDailyWordWriting.user.id.eq(dto.getUserId()));
        }

        if(dto.getSearchDate() != null) {
            LocalDateTime start = dto.getSearchDate().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime end = start.plusMonths(1).minusNanos(1);

            builder.and(qDailyWordWriting.createdAt.between(start, end));
        }

        if(dto.getIsActive() != null) {
            builder.and(qDailyWordWriting.isActive.eq(dto.getIsActive()));
        }

        List<DailyWordWriting> content = jpaQueryFactory
                .selectFrom(qDailyWordWriting)
                .join(qDailyWordWriting.dailyWord, qDailyWord).fetchJoin()
                .join(qDailyWordWriting.user, qUsers).fetchJoin()
                .join(qDailyWord.word, qWord).fetchJoin()
                .join(qWord.category, qWordCategory).fetchJoin()
                .join(qDailyWordWriting.writingGoal, qWritingGoal).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(qDailyWordWriting.count())
                        .from(qDailyWordWriting)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<DailyWordWriting> searchWritingDetailByDto(WritingSearchDto writingSearchDto) {
        QDailyWordWriting qDailyWordWritings = QDailyWordWriting.dailyWordWriting;
        QDailyWord qDailyWords = QDailyWord.dailyWord;
        QUser qUsers = QUser.user;
        QWord qWords = QWord.word;
        QWordCategory qWordCategories = QWordCategory.wordCategory;
        QWritingGoal qWritingGoals = QWritingGoal.writingGoal;

        LocalDateTime searchDate = writingSearchDto.getSearchDate();
        LocalDateTime start = searchDate.toLocalDate().atStartOfDay();
        LocalDateTime end = searchDate.toLocalDate().atTime(23, 59, 59);

        DailyWordWriting content = jpaQueryFactory
                .selectFrom(qDailyWordWritings)
                .join(qDailyWordWritings.dailyWord, qDailyWords).fetchJoin()
                .join(qDailyWordWritings.user, qUsers).fetchJoin()
                .join(qDailyWords.word, qWords).fetchJoin()
                .join(qWords.category, qWordCategories).fetchJoin()
                .join(qDailyWordWritings.writingGoal, qWritingGoals).fetchJoin()
                .where(
                    qDailyWordWritings.user.id.eq(writingSearchDto.getUserId()),
                    qDailyWordWritings.createdAt.between(start, end),
                    qDailyWordWritings.isActive.eq(writingSearchDto.getIsActive())
                )
                .fetchOne();

        return Optional.ofNullable(content);
    }
}
