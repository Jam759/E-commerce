package com.toy.E_commerce.store.entity;

import com.toy.E_commerce.global.entity.base.id.LongIdTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends LongIdTimeEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column(length = 200)
    private String description;

    private String imgPath;

}
