package com.feelory.feelory_backend.feedbacks.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import com.feelory.feelory_backend.writing.entity.DailyWordWriting;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 5000)
    private String content;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_word_writings_id", nullable = false)
    private DailyWordWriting dailyWordWriting;
}
