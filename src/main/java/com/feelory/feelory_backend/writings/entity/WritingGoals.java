package com.feelory.feelory_backend.writings.entity;

import com.feelory.feelory_backend.global.BaseEntity;
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
public class WritingGoals extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="name", nullable = false)
    private String name;

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
