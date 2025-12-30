package com.toy.E_commerce.auth.service.query;

import com.toy.E_commerce.auth.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenQueryService {
    public RefreshToken getByRefreshToken(String refreshToken) {
        return null;
    }
}
