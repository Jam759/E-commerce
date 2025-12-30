package com.toy.E_commerce.global.entity.lifecycle;

import com.toy.E_commerce.global.entity.lifecycle.interfaces.CustomSoftDeletable;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseSoftDeleteEntity
        extends BaseTimeEntity implements CustomSoftDeletable {

    protected LocalDateTime deletedAt;

}


