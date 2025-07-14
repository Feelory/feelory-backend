package com.feelory.feelory_backend.sample.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name="sample_table")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SampleTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "txt")
    private String txt;
}
