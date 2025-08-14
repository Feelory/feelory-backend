package com.feelory.feelory_backend.word.repository;

import com.feelory.feelory_backend.word.entity.QWordCategory;
import com.feelory.feelory_backend.word.entity.WordCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<WordCategory> searchCategoriesByIsActive(Boolean isActive, Pageable pageable) {

        QWordCategory qWordCategory = QWordCategory.wordCategory;

        BooleanBuilder builder = new BooleanBuilder();

        if (isActive != null) {
            builder.and(qWordCategory.isActive.eq(isActive));
        }

        List<WordCategory> content = jpaQueryFactory
                .selectFrom(qWordCategory)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                    .select(qWordCategory.count())
                    .from(qWordCategory)
                    .where(builder)
                    .fetchOne()
            ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
