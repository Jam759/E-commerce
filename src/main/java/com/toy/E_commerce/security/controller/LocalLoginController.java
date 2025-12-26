package com.toy.E_commerce.security.controller;

import com.toy.E_commerce.security.dto.request.LocalLoginRequest;
import com.toy.E_commerce.security.dto.response.JwtResponse;
import com.toy.E_commerce.security.entity.UserDetailsImpl;
import com.toy.E_commerce.security.service.SecurityCookieService;
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

    private final LocalLoginFacade facade;
    private final SecurityCookieService cookieService;

    @PostMapping("/v1/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LocalLoginRequest request) {

        JwtResponse body = facade.login(request);
        ResponseCookie cookie = cookieService.create(body.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(body);

    }

    @PostMapping("/v1/reissue")
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
    public ResponseEntity<JwtResponse> signUp(@Valid @RequestBody LocalSignUpRequest request ){

        JwtResponse body = facade.signUp(request);
        ResponseCookie cookie = cookieService.create(body.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(body);

    }

    @PostMapping("/v1/logout")
    public ResponseEntity<Void> logOut(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        facade.logOut(userDetails.getMember());
        ResponseCookie cookie = cookieService.delete();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();

    }

}
