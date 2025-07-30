package com.feelory.feelory_backend.writings.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import com.feelory.feelory_backend.words.entity.DailyWords;
import jakarta.persistence.*;
import lombok.*;

/*
    TODO. [TR-YOO] 사용자 인증 관련 처리 시 userId -> Join으로 변경
*/

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

    @Column(name="user_id", nullable = false)
    private Long userId;

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
