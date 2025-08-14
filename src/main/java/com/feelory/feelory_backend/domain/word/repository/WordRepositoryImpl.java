package com.feelory.feelory_backend.domain.word.repository;

import com.feelory.feelory_backend.domain.word.entity.QWord;
import com.feelory.feelory_backend.domain.word.entity.QWordCategory;
import com.feelory.feelory_backend.domain.word.entity.Word;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Word> searchWords(Long categoryId, Boolean isActive, Pageable pageable) {
        QWord qWords = QWord.word;

        BooleanBuilder builder = new BooleanBuilder();

        if (categoryId != null) {
            builder.and(qWords.category.id.eq(categoryId));
        }

        if (isActive != null) {
            builder.and(qWords.isActive.eq(isActive));
        }

        List<Word> content = jpaQueryFactory
                .selectFrom(qWords)
                .join(qWords.category, QWordCategory.wordCategory).fetchJoin()
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
