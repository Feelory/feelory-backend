package com.feelory.feelory_backend.sample.service;

import com.feelory.feelory_backend.sample.entity.SampleTable;
import com.feelory.feelory_backend.sample.repository.SampleTableRepository;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private SampleTableRepository sampleTableRepository;

    public SampleService(SampleTableRepository sampleTableRepository) {
        this.sampleTableRepository = sampleTableRepository;
    }

    public String getSampleTextFromDB() {
        Long id = 1L;
        SampleTable sampleData = sampleTableRepository.findById(id)
                .orElse(new SampleTable());

        return sampleData.getTxt();
    }
}
