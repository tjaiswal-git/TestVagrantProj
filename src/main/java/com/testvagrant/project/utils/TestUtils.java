package com.testvagrant.project.utils;

public class TestUtils {

    public static String generateUniqueCode(String prefix)
    {
        return prefix + System.currentTimeMillis();
    }
}
