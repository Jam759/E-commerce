package com.toy.E_commerce.global.entity.lifecycle;

import com.toy.E_commerce.global.util.TimeUtil;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseTimeEntity extends BaseCreatedEntity {

    protected LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = TimeUtil.getNowSeoulLocalDateTime();
    }

}
