package com.example.demo.common.pagination;

import java.io.Serializable;

import com.example.demo.common.utils.CommonUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String EMPTY = "";
    public static final String DESC = "desc";

    private String col;

    private String dir = DESC;

    public OrderItem(String column) {
        this.col = column;
        this.dir = DESC;
    }

    public static String asc(String column) {
        if (CommonUtil.empty(column)) {
            return EMPTY;
        }
        return column;
    }

    public static String desc(String column) {
        if (CommonUtil.empty(column)) {
            return EMPTY;
        }
        return column + " " + DESC;
    }

    public String getOrderBy(String prefix) {
        if (CommonUtil.notEmpty(col)) {
            if (DESC.equalsIgnoreCase(dir)) {
                return (CommonUtil.notEmpty(prefix) ? (prefix + ".") : "") + desc(col);
            } else {
                return (CommonUtil.notEmpty(prefix) ? (prefix + ".") : "") + asc(col);
            }
        }
        return EMPTY;
    }
}
