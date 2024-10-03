package com.lezhin.assignment.domain.contentevent.dto;

import com.lezhin.assignment.domain.contentevent.ContentEvent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ContentEventReqDto {

    @NotNull
    private Long contentId;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

}