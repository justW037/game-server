package com.example.demo.common.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinSpec {
    private final String joinType;   // "LEFT JOIN", "INNER JOIN"
    private final String table;      // "ROLE r"
    private final String condition;  // "t.ROLE_ID = r.ID"

    public String toSql() {
        return joinType + " " + table + " ON " + condition + " ";
    }
}