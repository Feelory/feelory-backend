package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.QWordCategories;
import com.feelory.feelory_backend.words.entity.QWords;
import com.feelory.feelory_backend.words.entity.Words;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WordsRepositoryImpl implements WordsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Words> searchWords(Long categoryId, Boolean isActive, Pageable pageable) {
        QWords qWords = QWords.words;

        BooleanBuilder builder = new BooleanBuilder();

        if (categoryId != null) {
            builder.and(qWords.category.id.eq(categoryId));
        }

        if (isActive != null) {
            builder.and(qWords.isActive.eq(isActive));
        }

        List<Words> content = jpaQueryFactory
                .selectFrom(qWords)
                .join(qWords.category, QWordCategories.wordCategories).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                    .select(qWords.count())
                    .from(qWords)
                    .where(builder)
                    .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }
}
