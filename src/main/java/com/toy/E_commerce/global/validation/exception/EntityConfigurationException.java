package com.toy.E_commerce.global.validation.exception;

public class EntityConfigurationException extends RuntimeException {
    public EntityConfigurationException(Class<?> entityClass, String reason) {
        super("[BOOT_MESSAGE] Entity: " + entityClass.getSimpleName() + " -> " + reason);
    }
}
