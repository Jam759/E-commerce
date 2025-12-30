package com.toy.E_commerce.member.repository;

import com.toy.E_commerce.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("""
        SELECT m
        FROM Member m
        LEFT JOIN FETCH m.loginInfo
        WHERE m.identityId = :identityId
    """)
    Optional<Member> findByIdentityIdWithLoginInfo(
            @Param("identityId") UUID identityId
    );

    boolean existsByEmail(String email);
}
