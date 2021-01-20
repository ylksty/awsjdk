package com.base.c08thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author joe
 * @create 2021-01-20 20:48
 */
/*
volatile 保证可见性
 */
public class a01volatile {
    public static class Foo{
        int numNo = 0;
        volatile int num = 0;
        AtomicInteger atomicIntegerNum = new AtomicInteger();
        public void change() {
            num = 77;
        }
        public void addOne() {
            numNo++;
            num++;
            atomicIntegerNum.incrementAndGet();
        }
    }

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /*
    不可见demo
     */
    public static void test1() {
        Foo foo = new Foo();
        new Thread(() -> {
            // 延迟，为了保证后面的线程执行，再修改值
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            foo.change();
        }, "A").start();
        // while里不能有代码，才能保证被阻塞
        while(foo.numNo == 0){
        }
        System.out.println(foo.numNo);
        System.out.println(Thread.currentThread().getName() + "\t 可见");
    }

    /*
    volatile 不保证原子性
    AtomicInterger 保证原子性
     */
    public static void test2() {
        Foo foo = new Foo();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 200; j++) {
                    foo.addOne();
                }
            }, String.valueOf(i)).start();
        }
        // 保证线程结束
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("foo.num = " + foo.num);
        System.out.println("foo.atomicIntegerNum = " + foo.atomicIntegerNum);
    }

    /*
    哪里用到 volatile
    没有 DCL双端加锁机制
     */
    public static void test3() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
//            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    /*
    哪里用到 volatile
    单例模式DCL（Double Check Lock 双端检锁机制）在加锁前和加锁后都进行一次判断
     */
    public static void test4() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                SingletonDemo.getInstanceDCL();
            }, String.valueOf(i)).start();
        }
    }

    public static class SingletonDemo{
        public SingletonDemo() {
            System.out.println(Thread.currentThread().getName() + "\t只能出现一次");
        }
        /*
        解决指令重排
        */
        private static volatile SingletonDemo instance = null;

        /*
        没有 DCL双端加锁机制
         */
        public static void getInstance() {
            if (instance == null) {
                instance = new SingletonDemo();
            }
        }
        /*
        DCL双端加锁机制 (Double Check Lock)
         */
        public static void getInstanceDCL() {
            if (instance == null) {
                // 线程锁住等待
                /*
                基本正确，还需要防止指令重排
                 */
                synchronized (SingletonDemo.class) {
                    System.out.println(Thread.currentThread().getName() + "\t获得锁，尝试实例");
                    if (instance == null) {
                        System.out.println(Thread.currentThread().getName() + "\t实例化。。。");
                        // 获得锁进入，发现没有实例
                        instance = new SingletonDemo();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t 获得实例");
        }
    }
}
