package com.toy.E_commerce.store.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdCreatedEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryStoreProduct extends LongIdCreatedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false, length = 50)
    private String name;

}
