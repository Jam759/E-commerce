package com.toy.E_commerce.order.entity;

import com.toy.E_commerce.product.entity.StoreSellDiscountEvent;
import com.toy.E_commerce.product.entity.StoreSellProduct;
import com.toy.E_commerce.product.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderItem {

    @JoinColumn(name = "product_order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_sell_product_id")
    private StoreSellProduct storeSellProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_sell_discount_event_id")
    private StoreSellDiscountEvent discountEvent;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

}
