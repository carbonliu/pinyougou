package com.itcast.demo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author LiuK
 * @date 2018/12/19 14:56
 * @description:
 */
public class MyMessageListener implements MessageListener {
    public void onMessage(Message message) {
        TextMessage textMessage=(TextMessage) message;
        try {
            System.out.println("接收到消息："+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
