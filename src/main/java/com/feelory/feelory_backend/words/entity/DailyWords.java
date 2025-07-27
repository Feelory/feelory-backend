package com.feelory.feelory_backend.words.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "daily_words")
public class DailyWords extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "words_id", nullable = false)
    private Words word;

    @Column(name="topic_date", nullable = false)
    private LocalDateTime topicDate;

    @Column(name="description")
    private String description;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;
}
