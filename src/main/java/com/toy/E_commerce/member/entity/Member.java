package com.toy.E_commerce.member.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.member.entity.enums.MemberRole;
import com.toy.E_commerce.member.entity.enums.OauthType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends LongIdSoftDeleteEntity {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private OauthType oauthType;

    @Column(nullable = false)
    private String oauthId;

    @Column(nullable = false)
    private MemberRole role;

    @Column(nullable = false)
    private UUID identityId;

    private String profileImgPath;

    @OneToOne(mappedBy = "member")
    @JoinColumn(name = "local_login_info_id")
    private LocalLoginInfo loginInfo;
}
