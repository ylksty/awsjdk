package joe.util.concurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class AtomicIntegerTest {
    /*
    1.什么是CAS

    - CAS全称
      - Compare-And-Set，是一条CPU并发原语
    - 功能
      - 判断内存某个位置值是否为预期值，如果是则更新为新的值，不是返回false，过程是原子的
    - CAS并发源语体现在Java语言中就是sun.miscUnSafe类中的各个方法，调用UnSafe类中的CAS方法，JVM会帮我实现CAS汇编指令，
        这是一种完全依赖于硬件功能，通过它实现了原子操作，再次强调，由于CAS是一种系统源语，源语属于操作系统用于范畴，是由若干个指令组成，
        用于完成某个功能的一个过程，并且源语的执行必须是连续的，在执行过程中不允许中断，也即是说CAS是一条原子指令，不会造成所谓的数据不一致的问题

     */
    @Test
    public void test1() {
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.compareAndSet(0,5)); // true
        System.out.println(atomicInteger.compareAndSet(0,2)); // false
        System.out.println(atomicInteger); // 5
        atomicInteger.getAndIncrement();
    }
}