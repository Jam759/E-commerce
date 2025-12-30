package com.toy.E_commerce.auth.dto.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogOutRequest {

    @NotBlank(message = "엑세스토큰은 비어있을 수 없습니다.")
    private String accessToken;

}
