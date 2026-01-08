package com.toy.E_commerce.auth.controller;

import com.toy.E_commerce.auth.dto.api.request.LocalLoginRequest;
import com.toy.E_commerce.auth.dto.api.request.LocalSignUpRequest;
import com.toy.E_commerce.auth.dto.api.request.LogOutRequest;
import com.toy.E_commerce.auth.dto.api.response.JwtResponse;
import com.toy.E_commerce.auth.facade.LocalAuthFacade;
import com.toy.E_commerce.auth.service.AuthCookieService;
import com.toy.E_commerce.global.annotation.ApiRole;
import com.toy.E_commerce.global.annotation.enums.SecurityRole;
import com.toy.E_commerce.security.entity.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/local")
@RequiredArgsConstructor
public class LocalLoginController {

    private final LocalAuthFacade facade;
    private final AuthCookieService cookieService;

    @PostMapping("/v1/login")
    @ApiRole(roles = { SecurityRole.PUBLIC })
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LocalLoginRequest request) {

        JwtResponse body = facade.login(request);
        ResponseCookie cookie = cookieService.create(body.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(body);

    }

    @PostMapping("/v1/reissue")
    @ApiRole(roles = { SecurityRole.PUBLIC })
    public ResponseEntity<JwtResponse> accessTokenReissue(
            @CookieValue(
                    name = "#{@securityCookieProperties.REFRESH_TOKEN_COOKIE_NAME}"
            )
            String refreshToken
    ) {

        JwtResponse body = facade.reissue(refreshToken);
        return ResponseEntity.ok(body);

    }

    @PostMapping("/v1/sign-up")
    @ApiRole(roles = { SecurityRole.PUBLIC })
    public ResponseEntity<JwtResponse> signUp(@Valid @RequestBody LocalSignUpRequest request ){

        JwtResponse body = facade.signUp(request);
        ResponseCookie cookie = cookieService.create(body.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(body);

    }

    @PostMapping("/v1/logout")
    @ApiRole(roles = { SecurityRole.AUTHENTICATED })
    public ResponseEntity<Void> logOut(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader("Authorization") String headerToken,
            @CookieValue(
                    name = "#{@securityCookieProperties.REFRESH_TOKEN_COOKIE_NAME}"
            )
            String refreshToken
    ) {

        facade.logOut(userDetails.getMember(),headerToken, refreshToken);
        ResponseCookie cookie = cookieService.delete();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();

    }

}
