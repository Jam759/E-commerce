package com.aca.ecommerce.product.entity;

import com.aca.ecommerce.common.entity.CommonGroupDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "product_category", indexes = {
        @Index(name = "idx_product_category_product_id", columnList = "product_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_name", nullable = false)
    private CommonGroupDetail productCategoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_level", nullable = false)
    private CommonGroupDetail productCategoryLevel;
}
