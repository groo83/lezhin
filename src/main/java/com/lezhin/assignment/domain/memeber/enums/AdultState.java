package com.lezhin.assignment.domain.memeber.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum AdultState {

    GENERAL("GENERAL", "일반"),
    ADULT("ADULT", "성인");

    private final String code;

    private final String type;

    public static AdultState of(String gender) {
        if(gender == null) {
            throw new IllegalArgumentException();
        }

        for (AdultState g : AdultState.values()) {
            if(g.code.equals(gender)) {
                return g;
            }
        }

        throw new IllegalArgumentException("일치하는 값이 없습니다.");
    }
}
