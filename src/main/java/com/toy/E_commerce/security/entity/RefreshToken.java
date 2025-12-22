package com.toy.E_commerce.security.entity;

import com.toy.E_commerce.global.entity.lifecycle.BaseTimeEntity;
import com.toy.E_commerce.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE refresh_token SET deleted_at = now() WHERE id = ?")
public class RefreshToken extends BaseTimeEntity {

    @Id
    private UUID id;// jti

    @Column(nullable = false, columnDefinition = "TEXT")
    private String encryptedToken;

    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member user;

}
