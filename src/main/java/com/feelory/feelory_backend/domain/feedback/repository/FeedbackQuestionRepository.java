package com.feelory.feelory_backend.domain.feedback.repository;

import com.feelory.feelory_backend.domain.feedback.entity.FeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackQuestionRepository extends JpaRepository<FeedbackQuestion, Long> {
}
