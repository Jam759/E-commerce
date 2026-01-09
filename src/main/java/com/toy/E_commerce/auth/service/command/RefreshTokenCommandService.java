package com.toy.E_commerce.auth.service.command;

import com.toy.E_commerce.auth.entity.RefreshToken;
import com.toy.E_commerce.auth.repository.db.RefreshTokenRepository;
import com.toy.E_commerce.auth.util.JwtUtil;
import com.toy.E_commerce.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenCommandService {

    private final RefreshTokenRepository repository;

    public void save(RefreshToken refreshToken) {
        repository.save(refreshToken);
    }

    public void delete(RefreshToken refreshToken) {
        repository.delete(refreshToken);
    }
}
