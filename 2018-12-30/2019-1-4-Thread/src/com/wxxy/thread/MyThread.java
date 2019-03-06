package com.wxxy.thread;

/**
 * @author LiuK
 * @date 2019/1/4 17:28
 * @description:
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("myThread"+i);
        }
    }
}
