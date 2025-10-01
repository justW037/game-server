package com.example.demo.common.contexts;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.utils.DatabaseUtil;

@Component
public class DatabaseContext {
    private static DatabaseUtil.DatabaseType dbType;

    @Autowired
    public DatabaseContext(DataSource dataSource) {
        dbType = DatabaseUtil.detectDatabaseType(dataSource);
    }

    public static DatabaseUtil.DatabaseType getDbType() {
        return dbType;
    }
}