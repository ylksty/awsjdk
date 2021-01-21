package com.base.c08thread.a05blockingqueue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author joe
 * @create 2021-01-14 15:28
 */
/*
 SynchronusQueue：不存储元素的阻塞队列，生产消费交替，吞吐量比LinkedBlockingQueue高
 同步队列demo，对标上面传统版demo
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                for (int i = 1; i < 4; i++) {
                    String item = "产品" + i;
                    System.out.println(Thread.currentThread().getName() + "\t生产:" + item);
                    synchronousQueue.put(item);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "生产者").start();

        new Thread(() -> {
            try {
                for (int i = 1; i < 6; i++) {
                    Object poll = synchronousQueue.poll(1, TimeUnit.SECONDS);
                    if (poll != null) {
                        System.out.println(Thread.currentThread().getName() + "\t消费:" + poll);
                    } else {
                        System.out.println(Thread.currentThread().getName() + "\t超时放弃");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "消费者" ).start();
    }
}
