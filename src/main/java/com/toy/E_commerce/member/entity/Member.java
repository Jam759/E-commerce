package com.toy.E_commerce.member.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.member.entity.enums.MemberRole;
import com.toy.E_commerce.member.entity.enums.OauthType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_at = now() WHERE id = ?")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return getId() != null
                && getId().equals(member.getId())
                && getIdentityId().equals(member.getIdentityId());
    }

}
