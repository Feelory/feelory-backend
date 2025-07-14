package com.feelory.feelory_backend.sample.repository;

import com.feelory.feelory_backend.sample.entity.SampleTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleTableRepository extends JpaRepository<SampleTable, Long>  {
}
