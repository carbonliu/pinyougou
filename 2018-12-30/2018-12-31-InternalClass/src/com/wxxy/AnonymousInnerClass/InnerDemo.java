package com.wxxy.AnonymousInnerClass;

/**
 * @author LiuK
 * @date 2018/12/31 16:08
 * @description:
 */
public class InnerDemo {
    public static void main(String[] args) {
        /**
         * 1.等号右边:是匿名内部类，定义并创建该接口的子类对象
         2.等号左边:是多态赋值,接口类型引用指向子类对象
         */
        FlyAble flyAble=new FlyAble() {
            @Override
            public void fly() {
                System.out.println("飞");
            }
        };
        //调用 fly方法,执行重写后的方法
        flyAble.fly();
        show(flyAble);
        show(new FlyAble() {
            @Override
            public void fly() {
                System.out.println("匿名内部类");
            }
        });
    }

    public  static void show(FlyAble flyAble){
        flyAble.fly();
    }
}
