package com.feelory.feelory_backend.feedbacks.repository;

import com.feelory.feelory_backend.feedbacks.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
