package com.toy.E_commerce.auth.repository.db;

import com.toy.E_commerce.auth.entity.RefreshToken;
import com.toy.E_commerce.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByIdAndDeletedAtIsNull(UUID id);

    Optional<RefreshToken> findByMemberAndDeletedAtIsNull(Member member);
}

