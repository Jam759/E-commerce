package com.toy.E_commerce.global.entity.lifecycle;

import com.toy.E_commerce.global.util.TimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseCreatedEntity {

    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = TimeUtil.getNowSeoulLocalDateTime();
    }

}
