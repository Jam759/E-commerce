package com.toy.E_commerce.security.service.impl;

import com.toy.E_commerce.security.properties.JwtProperties;
import com.toy.E_commerce.security.properties.SecurityCookieProperties;
import com.toy.E_commerce.security.service.SecurityCookieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityCookieServiceImpl implements SecurityCookieService {

    private final JwtProperties jwtProperties;
    private final SecurityCookieProperties cookieProperties;

    @Override
    public ResponseCookie create(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) //나중에 바꾸기
                .sameSite("None")
                .path("/auth/reissue")
                .maxAge(properties.getRefreshTokenTtl())
                .build();
    }

    @Override
    public ResponseCookie delete() {
        return ResponseCookie.from("refreshToken", "")
                .path("/auth/reissue")
                .maxAge(0)
                .build();
    }

}
