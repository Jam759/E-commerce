package com.toy.E_commerce.auth.repository.cache;


import com.toy.E_commerce.auth.dto.cache.AccessTokenBlackListCache;

import java.util.UUID;

public interface AccessTokenBlackListCacheStore {
    void setAccessTokenBlackListCache(AccessTokenBlackListCache cache);

    boolean isExist(UUID jti);

    AccessTokenBlackListCache getByJTI(UUID jti);

    void removeBlackList(UUID jti);
}

