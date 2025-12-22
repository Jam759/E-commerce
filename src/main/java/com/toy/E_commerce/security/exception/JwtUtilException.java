package com.toy.E_commerce.security.exception;

import lombok.Getter;

@Getter
public class JwtUtilException extends AuthBaseException {
    private final JwtUtilErrorCode errorCode;

    public JwtUtilException(JwtUtilErrorCode errorCode) {
        super("JwtUtilException");
        this.errorCode = errorCode;
    }

}

