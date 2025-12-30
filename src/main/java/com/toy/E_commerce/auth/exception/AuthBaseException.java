package com.toy.E_commerce.auth.exception;

import com.toy.E_commerce.global.exception.GlobalErrorCode;
import org.springframework.security.core.AuthenticationException;

public abstract class AuthBaseException extends AuthenticationException {

    public AuthBaseException(String msg) {
        super(msg);
    }

    public abstract GlobalErrorCode getErrorCode();
}