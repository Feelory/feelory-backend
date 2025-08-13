package com.feelory.feelory_backend.writing.repository;

import com.feelory.feelory_backend.writing.entity.QWritingGoal;
import com.feelory.feelory_backend.writing.entity.WritingGoal;
import com.feelory.feelory_backend.writing.dto.model.WritingGoalListSearchDto;
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
public class WritingGoalRepositoryImpl implements WritingGoalRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<WritingGoal> searchWritingGoalsByDto(WritingGoalListSearchDto searchDto) {
        QWritingGoal qWritingGoal = QWritingGoal.writingGoal;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qWritingGoal.user.id.eq(searchDto.getUserId()));

        LocalDateTime today = LocalDate.now().atStartOfDay();

        if(searchDto.getIsValidDate() != null) {
            builder.and(
                    qWritingGoal.startDate.loe(today)
                    .and(qWritingGoal.endDate.goe(today))
            );
        }

        if(searchDto.getIsActive() != null) {
            builder.and(qWritingGoal.isActive.eq(searchDto.getIsActive()));
        }

        Pageable pageable = searchDto.getPageable();
        List<WritingGoal> content = jpaQueryFactory
                .selectFrom(qWritingGoal)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                    .select(qWritingGoal.count())
                    .from(qWritingGoal)
                    .where(builder)
                    .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
