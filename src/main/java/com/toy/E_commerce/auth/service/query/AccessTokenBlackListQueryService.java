package com.toy.E_commerce.auth.service.query;

import com.toy.E_commerce.auth.repository.cache.AccessTokenBlackListCacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccessTokenBlackListQueryService {

    private final AccessTokenBlackListCacheStore cacheStore;

    public boolean isExistByToken(UUID jti) {
        return cacheStore.isExist(jti);
    }
}
