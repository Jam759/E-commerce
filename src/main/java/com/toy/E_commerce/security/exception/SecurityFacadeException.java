package com.toy.E_commerce.security.exception;

import lombok.Getter;

@Getter
public class SecurityFacadeException extends AuthBaseException {
    private final SecurityFacadeErrorCode errorCode;

    public SecurityFacadeException(SecurityFacadeErrorCode errorCode) {
        super("SecurityFacadeException");
        this.errorCode = errorCode;
    }
}
