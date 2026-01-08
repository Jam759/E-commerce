package com.toy.E_commerce.auth.service.query;

import com.toy.E_commerce.auth.entity.RefreshToken;
import com.toy.E_commerce.auth.exception.RefreshTokenErrorCode;
import com.toy.E_commerce.auth.exception.RefreshTokenException;
import com.toy.E_commerce.auth.repository.db.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenQueryService {

    private final RefreshTokenRepository repository;

    public RefreshToken getById(UUID jti) {
        return repository.findById(jti)
                .orElseThrow( () -> new RefreshTokenException(RefreshTokenErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }
}
