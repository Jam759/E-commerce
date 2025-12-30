package com.toy.E_commerce.product.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product SET deleted_at = now() WHERE id = ?")
public class Product extends LongIdSoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_large_id")
    private CategoryLarge categoryLarge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_middle_id")
    private CategoryMiddle categoryMiddle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_small_id")
    private CategorySmall categorySmall;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

}
