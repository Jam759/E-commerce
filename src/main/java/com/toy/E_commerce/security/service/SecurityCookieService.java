package com.toy.E_commerce.security.service;

import org.springframework.http.ResponseCookie;

public interface SecurityCookieService {

    public ResponseCookie create(String refreshToken);

    public ResponseCookie delete();

}
