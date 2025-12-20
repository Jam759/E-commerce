package com.toy.E_commerce.global.entity.lifecycle;

import com.toy.E_commerce.global.entity.lifecycle.interfaces.CustomSoftDeletable;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseSoftDeleteEntity
        extends BaseTimeEntity implements CustomSoftDeletable {

    protected LocalDateTime deletedAt;

}


