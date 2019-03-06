package com.wxxy.hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuK
 * @date 2019/1/2 11:07
 * @description:
 */
public class Demo {

    public static void main(String[] args) {
        int total=0;
        for(int i=0;i<4;i++){
            if(i==1)
                continue;
            if(i==2)
                break;
            total+=i;
        }
        System.out.println(total);

    }
}

