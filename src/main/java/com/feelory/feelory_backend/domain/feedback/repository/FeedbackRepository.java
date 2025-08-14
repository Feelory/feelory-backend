package com.feelory.feelory_backend.domain.feedback.repository;

import com.feelory.feelory_backend.domain.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
