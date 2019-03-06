package com.itcast.text;

import com.itcast.demo.QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author LiuK
 * @date 2018/12/19 14:46
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-producer.xml")
public class testQueueProducer {
    @Autowired
    private QueueProducer queueProducer;

    @Test
    public void  testsend(){
        queueProducer.sendTextMessage("hello world");
    }
}
