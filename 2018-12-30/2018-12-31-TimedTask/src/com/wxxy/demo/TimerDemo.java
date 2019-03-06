package com.wxxy.demo;

import java.util.Timer;

/**
 * @author LiuK
 * @date 2018/12/31 15:07
 * @description: 通过往Timer提交一个TimerTask的任务，同时指定多久后开始执行以及执行周期，我们可以开启一个定时任务。
 */
public class TimerDemo {
    public static void main(String[] args) {
        //创建定时器对象
        Timer timer=new Timer();
        //在三秒后执行MyTask类中的run方法，后面每10秒执行一次
        timer.schedule(new MyTask(),5000,1000);
    }
}
