package com.toy.E_commerce.global.exception;

public abstract class GlobalBaseException extends RuntimeException {
    public abstract GlobalErrorCode getErrorCode();
}
