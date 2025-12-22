package com.toy.E_commerce.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private LocalDateTime accessTokeExpiredAt;
    private LocalDateTime refreshTokeExpiredAt;
    
}
