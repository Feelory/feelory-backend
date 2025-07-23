package com.feelory.feelory_backend.words.repository;

import com.feelory.feelory_backend.words.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordsRepository extends JpaRepository<Words, Long>, WordsRepositoryCustom {
}
