package com.example.demo.common.utils;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


public class CommonUtil {

    public static boolean isNull(Object var) {
        return var == null;
    }

    public static boolean notNull(Object var) {
        return var != null;
    }

    public static boolean notEmpty(String var) {
        return var != null && var.length() > 0;
    }

    public static boolean empty(String var) {
        return var == null || var.length() == 0;
    }

    public static boolean notEmpty(Number var) {
        return null != var;
    }

    public static boolean empty(Number var) {
        return null == var;
    }

    public static boolean empty(Collection<?> var) {
        return null == var || var.isEmpty();
    }

    public static boolean notEmpty(Collection<?> var) {
        return null != var && !var.isEmpty();
    }

    public static boolean empty(Map<?, ?> var) {
        return null == var || var.isEmpty();
    }

    public static boolean notEmpty(Map<?, ?> var) {
        return null != var && !var.isEmpty();
    }

    public static boolean notEmpty(File file) {
        return null != file && file.exists();
    }

    public static boolean empty(File file) {
        return null == file || !file.exists();
    }

    public static boolean notEmpty(Object[] var) {
        return null != var && 0 < var.length;
    }

    public static boolean empty(Object[] var) {
        return null == var || 0 == var.length;
    }

    public static String nullToDefault(Object var, String def) {
        return null == var ? def : String.valueOf(var);
    }

    public static String nullToDefault(String var, String def) {
        return null == var ? def : var;
    }

    public static String nullToEmpty(Object var) {
        return nullToDefault(var, "");
    }

    public static String nullToEmpty(String var) {
        return nullToDefault(var, "");
    }

    public static <T> T getValueByIndex(LinkedHashMap<String, T> map, int index) {
        if (index < 0 || index >= map.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }

        int i = 0;
        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (i == index) {
                return entry.getValue();
            }
            i++;
        }
        return null;
    }
}
