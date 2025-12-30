package com.toy.E_commerce.auth.service;

import com.toy.E_commerce.auth.properties.SecurityCookieProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCookieService {

    private final SecurityCookieProperties cookieProperties;

    public ResponseCookie create(String refreshToken) {
        return ResponseCookie.from(cookieProperties.getREFRESH_TOKEN_COOKIE_NAME(), refreshToken)
                .httpOnly(true)
                .secure(cookieProperties.isREFRESH_TOKEN_SECURE())
                .sameSite(cookieProperties.getREFRESH_TOKEN_SAME_SITE())
                .path(cookieProperties.getREFRESH_TOKEN_COOKIE_PATH())
                .maxAge(cookieProperties.getREFRESH_TOKEN_MAX_AGE())
                .build();
    }

    public ResponseCookie delete() {
        return ResponseCookie.from(cookieProperties.getREFRESH_TOKEN_COOKIE_NAME(), "")
                .path(cookieProperties.getREFRESH_TOKEN_COOKIE_PATH())
                .maxAge(0)
                .build();
    }

}
