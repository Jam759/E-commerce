package com.toy.E_commerce.auth.exception;

import com.toy.E_commerce.global.exception.GlobalBaseException;
import lombok.Getter;

@Getter
public class RefreshTokenException extends GlobalBaseException {
    private final RefreshTokenErrorCode errorCode;

    public RefreshTokenException(RefreshTokenErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
