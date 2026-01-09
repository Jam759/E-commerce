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
        WHERE
            1=1 AND
            m.identityId = :identityId AND
            m.deletedAt IS NULL
    """)
    Optional<Member> findByIdentityIdWithLoginInfo(
            @Param("identityId") UUID identityId
    );

    boolean existsByEmailAndDeletedAtIsNull(String email);
}
