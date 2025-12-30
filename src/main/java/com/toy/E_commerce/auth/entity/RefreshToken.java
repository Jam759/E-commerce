package com.toy.E_commerce.auth.entity;

import com.toy.E_commerce.global.entity.lifecycle.BaseTimeEntity;
import com.toy.E_commerce.global.util.TimeUtil;
import com.toy.E_commerce.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE refresh_token SET deleted_at = now() WHERE id = ?")
public class RefreshToken extends BaseTimeEntity {

    @Id
    private UUID id;// jti

    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;

    private LocalDateTime expiryDate;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Long remainingTime() {
        return Duration.between(
                TimeUtil.getNowSeoulLocalDateTime(),
                this.expiryDate
        ).toDays();
    }

}
