package com.toy.E_commerce.auth.dto.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalLoginRequest {

    @NotBlank(message = "아이디는 비어있을 수 없습니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    private String password;

}
