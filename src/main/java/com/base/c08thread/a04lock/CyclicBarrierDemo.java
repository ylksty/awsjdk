package com.base.c08thread.a04lock;

import java.util.concurrent.CyclicBarrier;

/**
 * @author joe
 * @create 2021-01-14 12:06
 */
/*
4.6.CyclicBarrier
阻塞到一定数量，集体触发
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println(Thread.currentThread().getName() + "\t召唤神龙");
        });

        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "\t收集到龙珠");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "\t后续执行...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
