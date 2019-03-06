package com.pinyougou.search.service.impl;

import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.Arrays;

/**
 * @author LiuK
 * @date 2018/12/19 18:52
 * @description:
 */
@Component
public class ItemDeleteListener implements MessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {

        try {
            ObjectMessage objectMessage=(ObjectMessage) message;
            Long[] goodsIds=(Long[])objectMessage.getObject();
            System.out.println("监听到消息："+ goodsIds);
            itemSearchService.deleteByGoodsIds(Arrays.asList(goodsIds));
            System.out.println("成功删除索引库");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
