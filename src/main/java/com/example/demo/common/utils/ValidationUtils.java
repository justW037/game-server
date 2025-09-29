package com.example.demo.common.utils;

import java.util.Objects;

public final class ValidationUtils {
    private ValidationUtils() {}

    public static void notBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notSameUser(String fromUserId, String toUserId, String message) {
        if (Objects.equals(fromUserId, toUserId)) {
            throw new IllegalArgumentException(message);
        }
    }
}