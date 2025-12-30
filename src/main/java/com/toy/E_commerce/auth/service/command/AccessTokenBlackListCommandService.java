package com.toy.E_commerce.auth.service.command;

import com.toy.E_commerce.auth.dto.cache.AccessTokenBlackListCache;
import com.toy.E_commerce.auth.repository.cache.AccessTokenBlackListCacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenBlackListCommandService {

    private final AccessTokenBlackListCacheStore cacheStore;

    public void saveCache(AccessTokenBlackListCache accessTokenBlackListCache){
        cacheStore.setAccessTokenBlackListCache(accessTokenBlackListCache);
    }

}
