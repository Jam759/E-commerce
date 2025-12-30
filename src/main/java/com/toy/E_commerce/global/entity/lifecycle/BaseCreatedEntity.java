package com.toy.E_commerce.global.entity.lifecycle;

import com.toy.E_commerce.global.util.TimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
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
public abstract class BaseCreatedEntity {

    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = TimeUtil.getNowSeoulLocalDateTime();
    }

}
