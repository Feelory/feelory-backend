package com.feelory.feelory_backend.domain.writing.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import com.feelory.feelory_backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
    TODO. [TR-YOO] 사용자 인증 관련 처리 시 userId -> Join으로 변경
*/

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(
        name = "writing_goals",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "name"})
        }
)
public class WritingGoal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="duration", nullable = false)
    private int duration;

    @Column(name="description")
    private String description;

    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;
}
