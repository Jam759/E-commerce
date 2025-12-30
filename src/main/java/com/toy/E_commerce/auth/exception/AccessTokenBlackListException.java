package com.toy.E_commerce.auth.exception;

import lombok.Getter;

@Getter
public class AccessTokenBlackListException extends AuthBaseException {
    private final AccessTokenBlackListErrorCode errorCode;

    public AccessTokenBlackListException(AccessTokenBlackListErrorCode errorCode) {
        super("AccessTokenBlackListException");
        this.errorCode = errorCode;
    }
}
