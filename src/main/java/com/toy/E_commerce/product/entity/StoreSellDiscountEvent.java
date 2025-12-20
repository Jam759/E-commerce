package com.toy.E_commerce.product.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreSellDiscountEvent extends LongIdSoftDeleteEntity {

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private Short discountPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_sell_product_id")
    private StoreSellProduct storeSellProduct;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;


}
