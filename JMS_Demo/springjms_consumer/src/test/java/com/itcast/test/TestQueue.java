package com.itcast.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author LiuK
 * @date 2018/12/19 14:57
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-consumer-queue.xml")
public class TestQueue {

    @Test
    public void testQueue() throws IOException {
        System.in.read();
    }

}
