package com.example.demo.common.utils;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.example.demo.common.utils.DatabaseUtil.DatabaseType;


public class DatabaseUtil {

    @AllArgsConstructor
    @Getter
    public enum DatabaseType {
        MYSQL("mysql", "LIMIT %d, %d"),
        POSTGRESQL("postgresql", "OFFSET %d LIMIT %d"),
        ORACLE("oracle", "OFFSET %d ROWS FETCH NEXT %d ROWS ONLY"),
        SQLSERVER("sql server", "OFFSET %d ROWS FETCH NEXT %d ROWS ONLY"),
        H2("h2", "OFFSET %d ROWS FETCH NEXT %d ROWS ONLY"),
        UNKNOWN("", "");

        private final String keyword;
        private final String paginationFormat;

        public String formatPagination(int offset, int pageSize) {
            if (this == UNKNOWN) {
                throw new UnsupportedOperationException("Unsupported DB for pagination");
            }
            return String.format(paginationFormat, offset, pageSize);
        }

        public static DatabaseType fromProductName(String productName) {
            String name = productName.toLowerCase(Locale.ROOT);
            for (DatabaseType type : values()) {
                if (!type.keyword.isEmpty() && name.contains(type.keyword)) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }

    private static final Map<DataSource, DatabaseType> CACHE = new ConcurrentHashMap<>();

    public static DatabaseType detectDatabaseType(DataSource dataSource) {
        return CACHE.computeIfAbsent(dataSource, ds -> {
            try (Connection connection = ds.getConnection()) {
                String dbName = connection.getMetaData().getDatabaseProductName();
                return DatabaseType.fromProductName(dbName);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to detect database type", e);
            }
        });
    }

}
