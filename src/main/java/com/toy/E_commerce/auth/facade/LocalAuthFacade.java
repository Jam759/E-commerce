package com.toy.E_commerce.auth.facade;

import com.toy.E_commerce.auth.dto.api.request.LocalLoginRequest;
import com.toy.E_commerce.auth.dto.api.request.LocalSignUpRequest;
import com.toy.E_commerce.auth.dto.api.response.JwtResponse;
import com.toy.E_commerce.member.entity.Member;

public interface LocalAuthFacade {

    JwtResponse login(LocalLoginRequest request);

    JwtResponse reissue(String refreshToken);

    JwtResponse signUp(LocalSignUpRequest request);

    void logOut(Member member, String headerToken, String refreshToken);
}
