package com.lezhin.assignment.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    CONTENT_NOT_FOUND(400,"C001", "컨텐츠 정보가 없습니다."),
    USER_NOT_FOUND(400,"C002", "사용자 정보가 없습니다."),
    EXIST_EMAIL(404,"M001", "이미 이메일이 존재합니다."),

    ;

    private int status;
    private final String code;
    private final String message;
}
