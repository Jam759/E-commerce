package com.toy.E_commerce.order.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.order.entity.enums.OrderStatus;
import com.toy.E_commerce.product.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "product_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product_order SET deleted_at = now() WHERE id = ?")
public class ProductOrder extends LongIdSoftDeleteEntity {

    @JoinColumn(name = "orderer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

}
