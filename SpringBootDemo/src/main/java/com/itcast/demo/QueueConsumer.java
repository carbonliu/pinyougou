package com.itcast.demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author LiuK
 * @date 2018/12/20 15:11
 * @description:
 */
@Component
public class QueueConsumer {

    @JmsListener(destination = "itcast")
    public void readMessage(String text){
        System.out.println("接收到消息："+text);
    }

    @JmsListener(destination ="itcast_map" )
    public void readMap(Map map){
        System.out.println("news:"+map.get("name")+";"+map.get("tel")+";"+map.get("address"));
    }
}
