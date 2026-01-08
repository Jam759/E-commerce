package com.toy.E_commerce.global.annotation;

import com.toy.E_commerce.global.annotation.enums.SecurityRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 api 접근 권한 자동 수집 어노테이션
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRole {
    SecurityRole[] roles();
}
