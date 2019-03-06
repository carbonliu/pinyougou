package com.wxxy.internalClass;

/**
 * @author LiuK
 * @date 2018/12/31 15:58
 * @description:
 */
public class TestDemo {
    public static void main(String[] args) {
        //创建外部类对象
        Person person=new Person();
        person.setLive(false);
        //创建内部类对象
        Person.Heart heart=person.new Heart();
        heart.jump();
        person.setLive(true);
        heart.jump();
    }
}
