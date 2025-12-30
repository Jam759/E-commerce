package com.toy.E_commerce.product.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "store_sell_discount_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE store_sell_discount_event SET deleted_at = now() WHERE id = ?")
public class StoreSellDiscountEvent extends LongIdSoftDeleteEntity {

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private Short discountPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_sell_product_id")
    private StoreSellProduct storeSellProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


}
