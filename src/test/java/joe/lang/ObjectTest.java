package joe.lang;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ObjectTest {
    @Test
    public void deadLock() {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t获得锁:" + lockA + "\t尝试获得:" + lockB);
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockB) {
                    System.out.println("ObjectTest.deadLock");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t获得锁:" + lockB + "\t尝试获得:" + lockA);
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockA) {
                    System.out.println("ObjectTest.deadLock");
                }
            }
        }, "B").start();
    }

    public static void main(String[] args) {
        // main里，只有所有线程结束才会结束（猜测）
        deadLockDemo();
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("ObjectTest.main");
    }

    private static void deadLockDemo() {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t获得锁:" + lockA + "\t尝试获得:" + lockB);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockB) {
                    System.out.println("ObjectTest.deadLock");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t获得锁:" + lockB + "\t尝试获得:" + lockA);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockA) {
                    System.out.println("ObjectTest.deadLock");
                }
            }
        }, "B").start();
    }
}