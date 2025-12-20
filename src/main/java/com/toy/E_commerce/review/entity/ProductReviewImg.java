package com.toy.E_commerce.review.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.review.entity.enums.FileFormat;
import com.toy.E_commerce.review.entity.enums.FileUsage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReviewImg extends LongIdSoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_review_id")
    private ProductReview productReview;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FileFormat fileFormat;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FileUsage fileUsage;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String path;

}
