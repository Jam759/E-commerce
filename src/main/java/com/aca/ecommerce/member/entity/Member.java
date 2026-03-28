package com.aca.ecommerce.member.entity;

import com.aca.ecommerce.global.entity.BaseEntity;
import com.aca.ecommerce.member.enums.OAuthType;
import com.aca.ecommerce.member.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SuperBuilder
@Table(name = "member", indexes = {
        @Index(name = "idx_identity_id", columnList = "identity_id")
})
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "oauth_id", columnDefinition = "TEXT")
    private String oauthId;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_type")
    private OAuthType oauthType;

    @Column(name = "identity_id", length = 36, columnDefinition = "CHAR(36)")
    private String identityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "profile_img_path", length = 255)
    private String profileImgPath;

    @Column(name = "local_login_id", length = 100)
    private String localLoginId;

    @Column(name = "local_login_password", columnDefinition = "TEXT")
    private String localLoginPassword;
}
