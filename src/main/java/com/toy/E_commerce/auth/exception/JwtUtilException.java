package com.toy.E_commerce.auth.exception;

import lombok.Getter;

@Getter
public class JwtUtilException extends AuthBaseException {
    private final JwtUtilErrorCode errorCode;

    public JwtUtilException(JwtUtilErrorCode errorCode) {
        super("JwtUtilException");
        this.errorCode = errorCode;
    }

}

