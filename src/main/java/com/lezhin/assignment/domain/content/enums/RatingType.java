package com.lezhin.assignment.domain.content.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RatingType {

    GENERAL("GENERAL", "일반"),
    ADULT("ADULT", "성인");

    private final String code;
    private final String type;
}
