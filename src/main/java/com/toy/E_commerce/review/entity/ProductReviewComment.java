package com.toy.E_commerce.review.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import com.toy.E_commerce.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "product_review_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product_review_comment SET deleted_at = now() WHERE id = ?")
public class ProductReviewComment extends LongIdSoftDeleteEntity {

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "review_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductReview review;

    @Column(nullable = false, length = 200)
    private String content;

}
