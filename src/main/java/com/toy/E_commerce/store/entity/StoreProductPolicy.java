package com.toy.E_commerce.store.entity;


import com.toy.E_commerce.store.entity.enums.StorePolicyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreProductPolicy {

    @Id
    @GeneratedValue
    private Long id;

    private StorePolicyType policyType;

    @Column(nullable = false, length = 10000)
    private String description;
}
