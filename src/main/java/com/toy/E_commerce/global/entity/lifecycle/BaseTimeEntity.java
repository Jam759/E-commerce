package com.toy.E_commerce.global.entity.lifecycle;

import com.toy.E_commerce.global.util.TimeUtil;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
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
public abstract class BaseTimeEntity extends BaseCreatedEntity {

    protected LocalDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = TimeUtil.getNowSeoulLocalDateTime();
    }

}
