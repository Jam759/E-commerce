package com.toy.E_commerce.auth.dto.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSignUpRequest {

    private String loginId;

    private String password;

    private String email;

}
