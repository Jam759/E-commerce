package com.toy.E_commerce.store.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdCreatedEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "category_store_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryStoreProduct extends LongIdCreatedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false, length = 50)
    private String name;

}
