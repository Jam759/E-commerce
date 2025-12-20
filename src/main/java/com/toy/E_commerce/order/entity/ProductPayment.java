package com.toy.E_commerce.order.entity;

import com.toy.E_commerce.global.entity.lifecycle.BaseTimeEntity;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.order.entity.enums.PaymentStatus;
import com.toy.E_commerce.product.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPayment extends BaseTimeEntity {

    @Id
    private Long id;

    @MapsId
    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private Long paidAmount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private LocalDateTime paidAt;

}