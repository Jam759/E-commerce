package com.toy.E_commerce.member.repository;

import com.toy.E_commerce.member.entity.LocalLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocalLoginInfoRepository extends JpaRepository<LocalLoginInfo, Long> {

    @Query("""
        SELECT l
        FROM LocalLoginInfo l
        JOIN FETCH l.member
        WHERE l.loginId = :loginId AND l.password = :password
   \s""")
    Optional<LocalLoginInfo> findByLoginIdAndPasswordWithMember(String loginId, String password);

}
