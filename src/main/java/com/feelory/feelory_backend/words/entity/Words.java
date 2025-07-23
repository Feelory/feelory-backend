package com.feelory.feelory_backend.words.entity;

import com.feelory.feelory_backend.global.BaseEntity;
import com.feelory.feelory_backend.words.model.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "words")
public class Words extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_category_id", nullable = false)
    private WordCategories category;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;
}
