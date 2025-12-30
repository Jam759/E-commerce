package com.toy.E_commerce.product.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdCreatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@Table(name = "category_middle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryMiddle extends LongIdCreatedEntity {

    @Column(unique = true, length = 50, nullable = false)
    private String name;

}
