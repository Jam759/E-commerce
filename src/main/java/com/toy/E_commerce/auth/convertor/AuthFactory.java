package com.toy.E_commerce.auth.convertor;

import com.toy.E_commerce.auth.dto.application.TokenBundle;
import com.toy.E_commerce.auth.dto.application.TokenInfo;
import com.toy.E_commerce.auth.entity.RefreshToken;
import com.toy.E_commerce.member.entity.Member;

public class AuthFactory {

    public static RefreshToken toRefreshToken(Member user, TokenBundle bundle) {
        TokenInfo refreshToken = bundle.getRefreshToken();
        return RefreshToken.builder()
                .token(refreshToken.getToken())
                .id(refreshToken.getJti())
                .expiryDate(refreshToken.getTokenExpireAt())
                .member(user)
                .build();
    }

}
