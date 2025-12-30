package com.toy.E_commerce.auth.convertor;

import com.toy.E_commerce.auth.dto.api.response.JwtResponse;
import com.toy.E_commerce.auth.dto.application.TokenBundle;
import com.toy.E_commerce.auth.dto.application.TokenInfo;
import com.toy.E_commerce.auth.dto.cache.AccessTokenBlackListCache;
import com.toy.E_commerce.auth.entity.RefreshToken;
import com.toy.E_commerce.global.util.TimeUtil;
import com.toy.E_commerce.member.entity.Member;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthAssembler {

    public static JwtResponse toJwtResponse(TokenBundle bundle) {
        TokenInfo accessToken = bundle.getAccessToken();
        TokenInfo refreshToken = bundle.getRefreshToken();
        return JwtResponse.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .accessTokeExpiredAt(accessToken.getTokenExpireAt())
                .refreshTokeExpiredAt(refreshToken.getTokenExpireAt())
                .build();
    }

    public static JwtResponse toJwtResponse(TokenInfo accessToken, RefreshToken refreshToken) {
        return JwtResponse.builder()
                .accessToken(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .accessTokeExpiredAt(accessToken.getTokenExpireAt())
                .refreshTokeExpiredAt(refreshToken.getExpiryDate())
                .build();
    }

    public static AccessTokenBlackListCache toAccessTokenBlackListCache(Member member, TokenInfo accessToken){
        return AccessTokenBlackListCache.builder()
                .jti(accessToken.getJti())
                .token(accessToken.getToken())
                .logoutTime(TimeUtil.getNowSeoulLocalDateTime())
                .expiryDate(accessToken.getTokenExpireAt())
                .memberId(member.getId())
                .build();

    }

}
