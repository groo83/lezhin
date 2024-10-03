package com.lezhin.assignment.domain.content.service;

import com.lezhin.assignment.common.code.ErrorCode;
import com.lezhin.assignment.event.ContentEvent;
import com.lezhin.assignment.exception.BusinessException;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.dto.ContentReqDto;
import com.lezhin.assignment.domain.content.dto.ContentResDto;
import com.lezhin.assignment.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public ContentResDto registerContent(ContentReqDto reqDto) {
        Content content = contentRepository.save(reqDto.toEntity());

        return ContentResDto.fromEntity(content);
    }

    public ContentResDto getContentById(Long contentId) {
        Content content = findContent(contentId);

        // 조회 내역 테이블 Insert
        applicationEventPublisher.publishEvent(ContentEvent.CreateContent.of(contentId));

        return ContentResDto.fromEntity(content);
    }

    private Content findContent(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));
    }

    @Transactional
    public void deleteContentAndHistory(Long contentId) {
        contentRepository.deleteById(contentId);

        // 조회 내역 테이블 Delete
        applicationEventPublisher.publishEvent(ContentEvent.CreateContent.of(contentId));

    }

}
