package com.wxxy.thread;

/**
 * @author LiuK
 * @date 2019/1/4 17:31
 * @description:
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("MyRunnable"+i);
        }
    }
}
