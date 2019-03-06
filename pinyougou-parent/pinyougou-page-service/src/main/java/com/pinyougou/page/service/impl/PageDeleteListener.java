package com.pinyougou.page.service.impl;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * @author LiuK
 * @date 2018/12/19 21:09
 * @description:
 */
@Component
public class PageDeleteListener implements MessageListener{

    @Autowired
    private ItemPageService itemPageService;


    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage=(ObjectMessage)message;
        try {
            Long[] goodsIds=(Long[])objectMessage.getObject();
            System.out.println("ItemDeleteListener 监听接收到消息。。。"+goodsIds);
            boolean b = itemPageService.deleteItemHtml(goodsIds);
            System.out.println("网页删除结果："+b);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}