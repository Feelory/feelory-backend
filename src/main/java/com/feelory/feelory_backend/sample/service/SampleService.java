package com.feelory.feelory_backend.sample.service;

import com.feelory.feelory_backend.global.exception.exceptions.users.UserNotFoundException;
import com.feelory.feelory_backend.sample.entity.SampleTable;
import com.feelory.feelory_backend.sample.repository.SampleTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleTableRepository sampleTableRepository;

    public String getSampleTextFromDB() {
        Long id = 1L;
        SampleTable sampleData = sampleTableRepository.findById(id)
                .orElse(SampleTable.of("저장된 내용이 없습니다.","시스템"));

        return sampleData.getTxt();
    }

    public void insertSampleText(){
        SampleTable A = SampleTable.of("H2 DB 입출력 테스트","김강산");
        sampleTableRepository.save(A);
    }

    public void throwBaseException() {
        throw new UserNotFoundException();
    }

    public void throwUnknownException() {
        String text = null;
        text.length(); // NPE 발생
    }
}
