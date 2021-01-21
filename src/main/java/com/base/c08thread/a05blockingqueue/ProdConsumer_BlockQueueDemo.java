package com.base.c08thread.a05blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author joe
 * @create 2021-01-14 20:46
 */
/*
 阻塞队列demo
 1. ArrayBlockingQueue：数组结构，有界阻塞队列，FIFO
 2. LinkedBlockingQueue：链表结构，有界(Integer.MAX_VALUE)阻塞队列，FIFO，吞吐量比ArrayBlockingQueue高
 */
public class ProdConsumer_BlockQueueDemo {
    public static class MyResource{
        private volatile boolean FLAG = true;
        private AtomicInteger atomicInteger = new AtomicInteger();
        BlockingQueue<String> blockingQueue = null;

        public MyResource(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        // 生产
        // 1秒一个
        // 1秒不能入队，继续生产，或结束生产
        public void produce()throws Exception {
            String data = null;
            boolean offer;
            while(FLAG) {
                data = atomicInteger.incrementAndGet() + "";
                offer = blockingQueue.offer(data, 1, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println(Thread.currentThread().getName() + "\t入队成功 " + data);
                    try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t入队失败 " + data);
                    System.out.println(Thread.currentThread().getName() + "\t继续生产");
//                    System.out.println(Thread.currentThread().getName() + "\t结束生产");
//                    return;
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t收到，大老板通知退出");
        }

        // 消费
        // 1秒不能消费，接续消费或，退出消费
        public void consume()throws Exception {
            String data = null;
            while(FLAG) {
                data = blockingQueue.poll(1, TimeUnit.SECONDS);
                if (data == null || data.equalsIgnoreCase("")) {
                    System.out.println(Thread.currentThread().getName() + "\t消费失败！！！ ");
                    System.out.println(Thread.currentThread().getName() + "\t继续消费");
//                    System.out.println(Thread.currentThread().getName() + "\t退出消费");
//                    return;
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t消费成功 " + data);
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t收到，大老板通知退出");
        }
        public void stopSwitch() {
            FLAG = false;
        }
    }
    public static void main(String[] args) {
//        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(3)); // 3个位置
        MyResource myResource = new MyResource(new LinkedBlockingDeque<>(3)); // 3个位置

        new Thread(() -> {
            try {
                myResource.produce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            // 5秒后开始消费
            try { TimeUnit.SECONDS.sleep(6); } catch (InterruptedException e) { e.printStackTrace(); }
            try {
                myResource.consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();

        try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() + "\t大老板叫停。。。");
        myResource.stopSwitch();
    }
}
