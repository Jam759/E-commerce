package com.toy.E_commerce.member.exception;

import com.toy.E_commerce.global.exception.GlobalBaseException;
import com.toy.E_commerce.member.exception.code.MemberErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends GlobalBaseException {
    private MemberErrorCode errorCode;
}
