package com.demo.lottery.utils;


import java.util.Collection;
import java.util.Map;

public abstract class AssertUtils {
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        if(collection == null  || collection.isEmpty()) {
            throw new IllegalArgumentException("[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
