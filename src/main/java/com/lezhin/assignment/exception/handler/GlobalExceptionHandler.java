package com.lezhin.assignment.exception.handler;

import com.lezhin.assignment.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
        log.error("BusinessException", ex);

        Map<String, String> response = new HashMap<>();
        response.put("code", ex.getErrorCode().getCode());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response,  HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}