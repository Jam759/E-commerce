package com.toy.E_commerce.member.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.member.entity.enums.MemberRole;
import com.toy.E_commerce.member.entity.enums.OauthType;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Column(nullable = false)
    private String oauthId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID identityId;

    private String profileImgPath;

    @OneToOne(mappedBy = "member", fetch = FetchType.EAGER)
    private LocalLoginInfo loginInfo;

}
