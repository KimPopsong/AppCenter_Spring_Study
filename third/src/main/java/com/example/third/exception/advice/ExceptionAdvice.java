package com.example.third.exception.advice;

import com.example.third.exception.MemberException;
import com.example.third.exception.TeamException;
import com.example.third.model.command.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // 전역 컨트롤러
public class ExceptionAdvice {

    // 예외를 핸들링 -> 예외 처리
    @ExceptionHandler({TeamException.class, MemberException.class})  // 예외 클래스
    public ResponseEntity exceptionHandler(Exception e) {
        String message = e.getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }
}
