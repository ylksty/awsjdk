package com.base.c08thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author joe
 * @create 2021-01-20 21:19
 */
/*
 二、CAS
 */
public class a02cas {
    public static void main(String[] args) {
//        test1(); // 什么是CAS?
//        test2(); // ABA问题
        test3(); // ABA解决
    }

    /*
    ABA解决
    java.util.concurrent.atomic.AtomicStampedReference 时间戳原子引用
     */
    private static void test3() {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
        new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            int stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet(101, 100, stamp, stamp + 1);
        }, "t1").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean b = atomicStampedReference.compareAndSet(100, 2020, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "是否修改成功：" + b);
            System.out.println("当前版本：" + atomicStampedReference.getStamp());
        }, "t2").start();
    }

    /*
    ABA问题
     */
    private static void test2() {
        AtomicReference<Integer> integerAtomicReference = new AtomicReference<>(100);
        new Thread(() -> {
            integerAtomicReference.compareAndSet(100, 101);
            integerAtomicReference.compareAndSet(101, 100);
        }, "t1").start();
        new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(integerAtomicReference.compareAndSet(100, 2020));
        }, "t2").start();
    }

    /*
    什么是CAS
     */
    private static void test1() {
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.compareAndSet(0,5)); // true
        System.out.println(atomicInteger.compareAndSet(0,2)); // false
        System.out.println(atomicInteger); // 5
    }
}
