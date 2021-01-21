package com.base.c08thread.a06threadpool;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author joe
 * @create 2021-01-21 16:28
 */
public class ThreadCreatedDemo {
    public static class ThreadDemo extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "\t运行中...");
        }
    }

    public static void main(String[] args) {
        System.out.println("-----------------继承Thread-------------------");
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();

        System.out.println("-----------------实现Runnable接口-------------------");
        new Thread(() -> System.out.println(Thread.currentThread().getName() + "\t运行中..."), "RunnableDemo").start();

        System.out.println("-----------------实现Callable-------------------");
         /*
         why?
            1.为了让一些太慢的任务，独立线程去执行，最后再阻塞式获得结果
            2.为了同步获得数据
         */
        FutureTask<Integer> joe = new FutureTask<>(new IQTaskDemo("joe", 100));
        new Thread(joe, "A").start();

        int mainIQ = 100;

        // 任务没有返回会阻塞，放到主逻辑后面
        try {
            Integer joeIQ = joe.get();
            int res = mainIQ + joeIQ;
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @AllArgsConstructor
    public static class IQTaskDemo implements Callable<Integer> {
        private final String name;
        private final int iq;

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + "\t" + name);
            Thread.sleep(1000);
            return iq;
        }
    }
}
