package com.itcast.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuK
 * @date 2018/12/20 15:06
 * @description:
 */
@RestController
public class QueueController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @RequestMapping("/send")
    public void send(String text){
        jmsMessagingTemplate.convertAndSend("itcast",text);
    }

    @RequestMapping("/sendmap")
    public void sendMap(){
        Map map=new HashMap();
        map.put("name","叶小姐");
        map.put("tel","18267543456");
        map.put("address","北京市东城区");
        jmsMessagingTemplate.convertAndSend("itcast_map",map);
    }
}
