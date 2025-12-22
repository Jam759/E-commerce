package com.toy.E_commerce.member.exception.code;

import com.toy.E_commerce.global.exception.GlobalErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MemberErrorCode implements GlobalErrorCode {

    MEMBER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, 1004, "존재하지 않는 유저입니다.");

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }


}