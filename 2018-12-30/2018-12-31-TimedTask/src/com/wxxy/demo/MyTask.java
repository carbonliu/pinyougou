package com.wxxy.demo;

import java.util.TimerTask;

/**
 * @author LiuK
 * @date 2018/12/31 15:05
 * @description: 定时任务
 */
public class MyTask extends TimerTask{


    @Override
    public void run() {
        System.out.println("Hello World!");

    }
}
