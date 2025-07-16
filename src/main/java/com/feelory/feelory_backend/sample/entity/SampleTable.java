package com.feelory.feelory_backend.sample.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Builder
    private SampleTable(String txt, String name){
        this.txt = txt;
        this.name = name;
    }

    public static SampleTable of(String txt, String name){
        return SampleTable.builder()
                .txt(txt)
                .name(name)
                .build();
    }
}
