package com.wxxy.thread;

/**
 * @author LiuK
 * @date 2019/1/4 17:28
 * @description:
 */
public class Demo1 {
    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        MyRunnable runnable=new MyRunnable();
        Thread r=new Thread(runnable,"runnable");
        r.start();
        myThread.start();
        for (int i = 0; i < 100; i++) {
            System.out.println("main"+i);
        }
        Runnable runnable1=new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("runnable"+i);
                }
            }
        };
        new Thread(runnable1).start();
    }
}
