package com.toy.E_commerce.security.service;

import java.util.UUID;

public interface AccessTokenBlackListQueryService {

    boolean isExistByToken(UUID jti);

}
