package com.feelory.feelory_backend.writings.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import com.feelory.feelory_backend.users.entity.Users;
import com.feelory.feelory_backend.words.entity.DailyWords;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "daily_word_writings")
public class DailyWordWritings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_words_id", nullable = false)
    private DailyWords dailyWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_goals_id", nullable = false)
    private WritingGoals writingGoal;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="visibility", nullable = false)
    private Boolean visibility;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;
}
