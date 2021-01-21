package com.base.c08thread;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author joe
 * @create 2021-01-20 21:57
 */
/*
 三、集合类不安全问题
 */
public class a03arrayerr {
    public static void main(String[] args) {
//        test2();
        test1();
    }

    private static void test2() {
//        HashSet<String> set = new HashSet<>();
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    /*
    ArrayList 的集合不安全并解决
     */
    private static void test1() {
//        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(); // 写时赋值，解决
//        List<String> list = Collections.synchronizedList(new ArrayList<>()); // 可以成功，性能问题
//        Vector<String> list = new Vector<>(); // 可以成功，性能问题
        ArrayList<String> list = new ArrayList<>(); // java.util.ConcurrentModificationException
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
