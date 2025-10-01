package com.example.demo.common.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;

public class EntityMetadataResolver {

    public static String resolveTableName(Class<?> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null && table.name() != null && !table.name().isBlank()) {
            return table.name();
        }
        return entityClass.getSimpleName().toUpperCase(); // fallback
    }

    public static String resolvePrimaryKey(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                Column column = field.getAnnotation(Column.class);
                if (column != null && column.name() != null && !column.name().isBlank()) {
                    return column.name();
                }
                return field.getName().toUpperCase();
            }
        }
        throw new IllegalStateException(
            "Entity " + entityClass.getName() + " has no @Id field"
        );
    }
}