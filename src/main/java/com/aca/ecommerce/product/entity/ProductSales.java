package com.aca.ecommerce.product.entity;

import com.aca.ecommerce.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@Table(name = "product_sales", indexes = {
        @Index(name = "idx_ps_product_id", columnList = "product_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSales extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ps_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity_sum", nullable = false)
    private Long quantitySum;

    @Column(name = "order_count", nullable = false)
    private Integer orderCount;

    @Column(name = "gross_amount", nullable = false)
    private Long grossAmount;

    @Column(name = "net_amount", nullable = false)
    private Long netAmount;

    @Column(name = "cancel_amount", nullable = false)
    private Long cancelAmount;

    @Column(name = "refund_amount", nullable = false)
    private Long refundAmount;

    @Column(name = "sales_date", nullable = false)
    private LocalDateTime salesDate;

    @Column(name = "updated_by")
    private Long updatedBy;
}
