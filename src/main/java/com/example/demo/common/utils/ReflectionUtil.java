package com.example.demo.common.utils;

import java.lang.reflect.Field;

public class ReflectionUtil {
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object obj, String fieldName) {
        if (obj == null || fieldName == null) return null;
        try {
            Field field = getField(obj.getClass(), fieldName);
            if (field == null) {
                return null;
            }
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access field " + fieldName + " of " + obj.getClass().getName(), e);
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // check cha
            }
        }
        return null;
    }
}