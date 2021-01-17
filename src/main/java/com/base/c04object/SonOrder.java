package com.base.c04object;

/**
 * @author joe
 * @create 2021-01-17 09:00
 */
public class SonOrder extends Father {
    public static void main(String[] args) {
        System.out.println("------------------------------------");
        SonOrder sonOrder = new SonOrder();
        System.out.println("------------------------------------");
        SonOrder sonOrder1 = new SonOrder();
    }
    private int i = test();
    private static int j = method();

    static {
        System.out.println("(6)");
    }
    SonOrder() {
        System.out.println("(7)");
    }
    {
        System.out.println("(8)");
    }
    // 重写
    public int test() {
        System.out.println("(9)");
        return 1;
    }
    public static int method() {
        System.out.println("(10)");
        return 1;
    }
}
class Father{
    private int i = test();
    private static int j = method();

    static {
        System.out.println("(1)");
    }
    Father() {
        System.out.println("(2)");
    }
    {
        System.out.println("(3)");
    }
    public int test() {
        System.out.println("(4)");
        return 1;
    }
    public static int method() {
        System.out.println("(5)");
        return 1;
    }
}
