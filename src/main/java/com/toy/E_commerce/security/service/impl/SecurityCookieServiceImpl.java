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

    private final SecurityCookieProperties cookieProperties;

    @Override
    public ResponseCookie create(String refreshToken) {
        return ResponseCookie.from(cookieProperties.getREFRESH_TOKEN_COOKIE_NAME(), refreshToken)
                .httpOnly(true)
                .secure(cookieProperties.isREFRESH_TOKEN_SECURE())
                .sameSite(cookieProperties.getREFRESH_TOKEN_SAME_SITE())
                .path(cookieProperties.getREFRESH_TOKEN_COOKIE_PATH())
                .maxAge(cookieProperties.getREFRESH_TOKEN_MAX_AGE())
                .build();
    }

    @Override
    public ResponseCookie delete() {
        return ResponseCookie.from(cookieProperties.getREFRESH_TOKEN_COOKIE_NAME(), "")
                .path(cookieProperties.getREFRESH_TOKEN_COOKIE_PATH())
                .maxAge(0)
                .build();
    }

}
