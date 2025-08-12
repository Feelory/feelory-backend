package com.feelory.feelory_backend.feedbacks.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import com.feelory.feelory_backend.global.exception.exceptions.BaseException;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedbacks extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 100)
    private String content;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_word_writings_id", nullable = false)
    private DailyWordWritings dailyWordWriting;
}
