package com.itcast.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author LiuK
 * @date 2018/12/19 11:25
 * @description: 消息生产者
 */
public class TopicProducer {
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.132:61616");
        //2.获取连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.获取session (参数1：是否启动事务，参数2：消息确认模式)
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        //5.创建队列对象
       Topic topic=session.createTopic("test-topic");
        //6.创建消息生产者
        MessageProducer producer=session.createProducer(topic);
        //7.创建消息
        TextMessage textMessage=session.createTextMessage("welcome use JmsAPI");
        //8.发送消息
        producer.send(textMessage);
        //9。关闭资源
        producer.close();
        session.close();
        connection.close();
    }
}
