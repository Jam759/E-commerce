package com.toy.E_commerce.security.controller;

import com.toy.E_commerce.security.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/local")
@RequiredArgsConstructor
public class LocalLoginController {


    @PostMapping("/v1/login")
    public ResponseEntity<LoginResponse> login(){

    }

}
