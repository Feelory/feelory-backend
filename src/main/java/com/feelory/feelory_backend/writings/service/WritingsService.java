package com.feelory.feelory_backend.writings.service;

import com.feelory.feelory_backend.words.entity.Words;
import com.feelory.feelory_backend.writings.entity.DailyWordWritings;
import com.feelory.feelory_backend.writings.model.UserWritingListRequest;
import com.feelory.feelory_backend.writings.model.UserWritingListResponse;
import com.feelory.feelory_backend.writings.repository.DailyWordWritingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WritingsService {

    private final DailyWordWritingsRepository dailyWordWritingsRepository;

    public UserWritingListResponse getUserWritings(UserWritingListRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<DailyWordWritings> writings = dailyWordWritingsRepository.searchWritings(request.getUserId(), true, pageable);

        return UserWritingListResponse.fromPage(writings);
    }
}
