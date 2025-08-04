package com.feelory.feelory_backend.writings.repository;

import com.feelory.feelory_backend.writings.entity.QWritingGoals;
import com.feelory.feelory_backend.writings.entity.WritingGoals;
import com.feelory.feelory_backend.writings.dto.model.WritingGoalListSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WritingGoalsRepositoryImpl implements WritingGoalsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<WritingGoals> searchWritingGoalsByDto(WritingGoalListSearch searchDto) {
        QWritingGoals qWritingGoals = QWritingGoals.writingGoals;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qWritingGoals.user.id.eq(searchDto.getUserId()));

        LocalDateTime today = LocalDate.now().atStartOfDay();

        if(searchDto.getIsValidDate() != null) {
            builder.and(
                    qWritingGoals.startDate.loe(today)
                    .and(qWritingGoals.endDate.goe(today))
            );
        }

        if(searchDto.getIsActive() != null) {
            builder.and(qWritingGoals.isActive.eq(searchDto.getIsActive()));
        }

        Pageable pageable = searchDto.getPageable();
        List<WritingGoals> content = jpaQueryFactory
                .selectFrom(qWritingGoals)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                    .select(qWritingGoals.count())
                    .from(qWritingGoals)
                    .where(builder)
                    .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
