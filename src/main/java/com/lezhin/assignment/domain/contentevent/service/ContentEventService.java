package com.lezhin.assignment.domain.contentevent.service;

import com.lezhin.assignment.common.code.ErrorCode;
import com.lezhin.assignment.domain.content.Content;
import com.lezhin.assignment.domain.content.repository.ContentRepository;
import com.lezhin.assignment.domain.contentevent.ContentEvent;
import com.lezhin.assignment.domain.contentevent.dto.ContentEventReqDto;
import com.lezhin.assignment.domain.contentevent.dto.ContentEventResDto;
import com.lezhin.assignment.domain.contentevent.repository.ContentEventRepository;
import com.lezhin.assignment.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentEventService {

    private final ContentEventRepository eventRepository;
    private final ContentRepository contentRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ContentEventResDto registerEvent(ContentEventReqDto reqDto) {

        Content content = contentRepository.findById(reqDto.getContentId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CONTENT_NOT_FOUND));

        LocalDateTime startDate = LocalDateTime.parse(reqDto.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(reqDto.getEndDate(), formatter);

        ContentEvent contentEvent = ContentEvent.builder()
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        ContentEvent saved = eventRepository.save(contentEvent);
        return ContentEventResDto.fromEntity(saved);
    }
}
