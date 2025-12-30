package com.toy.E_commerce.auth.dto.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokeExpiredAt;
    private LocalDateTime refreshTokeExpiredAt;
    
}
