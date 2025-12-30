package com.toy.E_commerce.product.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.product.entity.enums.Currency;
import com.toy.E_commerce.product.entity.enums.ProductStatus;
import com.toy.E_commerce.store.entity.CategoryStoreProduct;
import com.toy.E_commerce.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "store_sell_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE store_sell_product SET deleted_at = now() WHERE id = ?")
public class StoreSellProduct extends LongIdSoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_category_id")
    private CategoryStoreProduct categoryStoreProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private Long inventoryCount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

}
