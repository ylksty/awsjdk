package com.jvm.gc;

import java.util.concurrent.TimeUnit;

/**
 * @author joe
 * @create 2021-01-16 16:36
 */
public class HelloGC {
    public static void main(String[] args) {
        System.out.println("hello GC");
        try { TimeUnit.SECONDS.sleep(Integer.MAX_VALUE); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
