package com.t1.example.reqresloggerexample.utils;

public class ThreadUtils {
    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
