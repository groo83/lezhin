package com.lezhin.assignment.domain.contentevent.dto;


import com.lezhin.assignment.domain.contentevent.ContentEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentEventResDto {

    private Long contentId;

    private String startDate;

    private String endDate;

    public static ContentEventResDto fromEntity(ContentEvent contentEvent) {
        return ContentEventResDto.builder()
                .contentId(contentEvent.getContent().getId())
                .startDate(contentEvent.getStartDate().toString())
                .endDate(contentEvent.getEndDate().toString())
                .build();
    }
}
