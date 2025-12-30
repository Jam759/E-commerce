package com.toy.E_commerce.auth.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

    private UUID jti;
    private String token;
    private LocalDateTime tokenExpireAt;


}
