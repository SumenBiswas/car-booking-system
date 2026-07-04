package com.sumen.Util;

public class StringUtils {
    private StringUtils() {}

    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
