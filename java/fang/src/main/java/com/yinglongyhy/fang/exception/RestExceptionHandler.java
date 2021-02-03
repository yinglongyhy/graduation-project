package com.yinglongyhy.fang.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<RestError> restApiExceptionHandler(RestApiException e) {
        RestError error = new RestError(e.getSuperCode() + ":" + e.getSubCode(), "请求失败");

        log.debug(error.toString());

        if (null != e.getMessage()) {
            error.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
