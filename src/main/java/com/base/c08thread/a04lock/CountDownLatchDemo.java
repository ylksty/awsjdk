package com.base.c08thread.a04lock;

import com.advanced.c10annotation.enumbox.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * @author joe
 * @create 2021-01-14 11:23
 */
/*
 4.5.CountDownLatch
 让一些线程阻塞，直到另一些线程完成一系列操作后才被唤醒
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        useEnum();
//        problemFixed();
//        problem();
    }

    private static void useEnum() {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i < 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 灭。。。");
                countDownLatch.countDown();
            }, CountryEnum.get_CountryEnum(i).getRetMsg()).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t 秦统一六国");
    }

    private static void problemFixed() {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 1; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t 班长关门");
    }

    private static void problem() {
        for (int i = 1; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 离开教室");
            }, String.valueOf(i)).start();
        }

        System.out.println(Thread.currentThread().getName() + "\t 班长关门");
    }
}
