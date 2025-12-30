package com.toy.E_commerce.store.entity;


import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.store.entity.enums.StorePolicyType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "store_product_policy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE store_product_policy SET deleted_at = now() WHERE id = ?")
public class StoreProductPolicy extends LongIdSoftDeleteEntity {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StorePolicyType policyType;

    @Column(nullable = false, length = 10000)
    private String description;
}
