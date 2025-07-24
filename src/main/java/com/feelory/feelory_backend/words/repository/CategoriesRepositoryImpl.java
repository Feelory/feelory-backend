package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.QWordCategories;
import com.feelory.feelory_backend.words.entity.WordCategories;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoriesRepositoryImpl implements CategoriesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<WordCategories> searchCategoriesByIsActive(Boolean isActive, Pageable pageable) {

        QWordCategories qWordCategories = QWordCategories.wordCategories;

        BooleanBuilder builder = new BooleanBuilder();

        if (isActive != null) {
            builder.and(qWordCategories.isActive.eq(isActive));
        }

        List<WordCategories> content = jpaQueryFactory
                .selectFrom(qWordCategories)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                    .select(qWordCategories.count())
                    .from(qWordCategories)
                    .where(builder)
                    .fetchOne()
            ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
