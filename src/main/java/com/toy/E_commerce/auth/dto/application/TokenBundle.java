package com.toy.E_commerce.auth.dto.application;

import com.toy.E_commerce.auth.dto.application.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class TokenBundle {
    private final Map<TokenType, TokenInfo> tokens;

    public TokenInfo getAccessToken() {
        return tokens.get(TokenType.ACCESS);
    }

    public TokenInfo getRefreshToken() {
        return tokens.get(TokenType.REFRESH);
    }
}
