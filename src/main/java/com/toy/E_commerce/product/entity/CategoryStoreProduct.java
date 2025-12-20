package com.toy.E_commerce.product.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdCreatedEntity;
import com.toy.E_commerce.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryStoreProduct extends LongIdCreatedEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

}
