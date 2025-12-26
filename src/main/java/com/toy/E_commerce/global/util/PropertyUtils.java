package com.toy.E_commerce.global.util;

import org.springframework.core.env.Environment;

import java.util.Objects;
import java.util.function.Function;

public final class PropertyUtils {

    private PropertyUtils() {}

    public static String getRequired(Environment env, String key) {
        String value = env.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required property is missing: " + key
            );
        }
        return value;
    }

    public static boolean getBoolean(Environment env, String key) {
        return Boolean.parseBoolean(getRequired(env, key));
    }

    public static long getLong(Environment env, String key) {
        try {
            return Long.parseLong(getRequired(env, key));
        } catch (NumberFormatException e) {
            throw new IllegalStateException(
                    "Property must be a number: " + key
            );
        }
    }

    public static <T> T getRequired(
            Environment env,
            String key,
            Function<String, T> converter
    ) {
        try {
            return converter.apply(getRequired(env, key));
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Invalid property value: " + key
            );
        }
    }
}
