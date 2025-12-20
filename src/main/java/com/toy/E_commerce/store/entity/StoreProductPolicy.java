package com.toy.E_commerce.store.entity;


import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.store.entity.enums.StorePolicyType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreProductPolicy extends LongIdSoftDeleteEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StorePolicyType policyType;

    @Column(nullable = false, length = 10000)
    private String description;
}
