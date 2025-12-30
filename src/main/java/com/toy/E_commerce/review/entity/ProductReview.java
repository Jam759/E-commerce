package com.toy.E_commerce.review.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.member.entity.Member;
import com.toy.E_commerce.product.entity.Product;
import com.toy.E_commerce.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "product_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product_review SET deleted_at = now() WHERE id = ?")
public class ProductReview extends LongIdSoftDeleteEntity {

    @JoinColumn(name = "reviewer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private Long likeCount;

}
