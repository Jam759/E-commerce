package com.toy.E_commerce.security.service.impl;

import com.toy.E_commerce.security.repository.cache.AccessTokenBlackListCacheStore;
import com.toy.E_commerce.security.service.AccessTokenBlackListQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccessTokenBlackListQueryServiceImpl implements AccessTokenBlackListQueryService {

    private final AccessTokenBlackListCacheStore cacheStore;

    @Override
    public boolean isExistByToken(UUID jti) {
        return cacheStore.isExist(jti);
    }
}
