package com.toy.E_commerce.product.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdCreatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySmall extends LongIdCreatedEntity {

    @Column(unique = true, length = 50, nullable = false)
    private String name;

}
