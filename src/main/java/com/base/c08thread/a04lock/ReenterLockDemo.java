package com.base.c08thread.a04lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author joe
 * @create 2021-01-21 09:57
 */
/*
 4.1.公平锁、非公平锁
 4.2.可重入锁（递归锁）
 */
public class ReenterLockDemo {
    public static class Phone {
        // --- synchronized
        public synchronized void sendSMS() {
            System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
            sendEmail();
        }
        public synchronized void sendEmail() {
            System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
        }

        // --- reentrantLock
        Lock reentrantLock = new ReentrantLock(true);
        public void get() {
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t invoked get()");
                set();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }

        private void set() {
            reentrantLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t invoked set()");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("-----------------synchronized-------------------");
        Phone phone = new Phone();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                phone.sendSMS(); // 非公平锁，可重入
            }, String.valueOf(i)).start();
        }

        System.out.println("-----------------reentrantLock-------------------");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                phone.get(); // 公平锁，可重入
            }, String.valueOf(i)).start();
        }
    }
}
