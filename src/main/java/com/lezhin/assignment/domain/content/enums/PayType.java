package com.lezhin.assignment.domain.content.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PayType {

    FREE("FREE", "무료"),
    PAID("PAID", "유료");

    private final String code;
    private final String type;
}
