package com.lezhin.assignment.domain.memeber.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

    USER("USER", "사용자"),
    ADMIN("ADMIN", "관리자");

    private final String code;

    private final String type;
}
