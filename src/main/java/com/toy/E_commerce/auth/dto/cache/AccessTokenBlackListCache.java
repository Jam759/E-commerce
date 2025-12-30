package com.toy.E_commerce.auth.dto.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenBlackListCache {
    private UUID jti;
    private String token;
    private LocalDateTime logoutTime;
    private LocalDateTime expiryDate;
    private Long memberId;

    // 캐시에 넣을 때 사용할 TTL (밀리초)
    private Long ttlMillis;

    public void setTtlMillis(Long ttlMillis) {
        this.ttlMillis = ttlMillis;
    }

}
