package com.lezhin.assignment.domain.contentview.dto;

import com.lezhin.assignment.domain.contentview.ContentViewHistory;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ContentViewHistoryDto {

    private Long memberId;

    private Long contentId;

    private String createdDate;

    public static ContentViewHistoryDto fromEntity(ContentViewHistory history) {
        return ContentViewHistoryDto.builder()
                .contentId(history.getContentId())
                .memberId(history.getMemberId())
                .createdDate(history.getCreatedDate().toString())
                .build();
    }

    public static List<ContentViewHistoryDto> fromEntity(List<ContentViewHistory> historyList) {
        return historyList.stream()
                .map(ContentViewHistoryDto::fromEntity)
                .collect(Collectors.toList());
    }
}
