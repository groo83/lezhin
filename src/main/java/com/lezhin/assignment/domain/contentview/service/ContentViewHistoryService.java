package com.lezhin.assignment.domain.contentview.service;

import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.content.repository.ContentRepository;
import com.lezhin.assignment.domain.contentview.ContentViewHistory;
import com.lezhin.assignment.domain.contentview.dto.ContentViewHistoryDto;
import com.lezhin.assignment.domain.contentview.repository.ContentViewHistoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentViewHistoryService {

    private final ContentViewHistoryRepository viewHistoryRepository;
    private final ContentRepository contentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registerViewHistory(ContentViewHistory viewHistory) {
        viewHistoryRepository.save(viewHistory);
    }

    public List<ContentViewHistoryDto> getContentViewHistory(Long contentId) {
        List<ContentViewHistory> historyList = viewHistoryRepository.findByContentId(contentId);
        return ContentViewHistoryDto.fromEntity(historyList);
    }

    public List<ContentResDto> getTopViewedContent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Object[]> results = viewHistoryRepository.findTopNContentByViewCount(pageable);

        List<Long> contentIds = results.stream()
                .map(result -> (Long) result[0]) // 첫 번째 열은 contentId
                .collect(Collectors.toList());

        List<Content> contents = contentRepository.findAllById(contentIds);

        return contents.stream()
                .map(ContentResDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteViewHistory(Long contentId) {
        viewHistoryRepository.deleteById(contentId);
    }
}
