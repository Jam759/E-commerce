package com.toy.E_commerce.store.entity;

import com.toy.E_commerce.global.entity.lifecycle.BaseTimeEntity;
import com.toy.E_commerce.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {

    @Id
    private Long id;

    @MapsId
    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column(length = 200)
    private String description;

    private String imgPath;

}
