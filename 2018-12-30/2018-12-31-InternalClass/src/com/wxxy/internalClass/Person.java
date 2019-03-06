package com.wxxy.internalClass;

/**
 * @author LiuK
 * @date 2018/12/31 15:54
 * @description: 内部类
 */
public class Person {
    private boolean live=true;

    //内部类
    class Heart{
        public void jump() {
            //直接访问外部类成员变量
            if (live) {
                System.out.println("心脏在跳动");
            } else {
                System.out.println("死了");
            }
        }
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
