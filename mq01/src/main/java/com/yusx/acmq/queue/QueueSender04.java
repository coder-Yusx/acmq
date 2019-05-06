package com.yusx.acmq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//批量自动确认
public class QueueSender04 {
    public static void main(String[] args) {

        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageProducer messageProducer = null;

        String broker_url = "tcp://47.92.239.77:61616";
        String queue_name = "myQueue";
        String msg = "你好啊，梦想家";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker_url);

        try {
            connection = connectionFactory.createConnection();

            //批量自动确认
            session = connection.createSession(Boolean.FALSE,Session.DUPS_OK_ACKNOWLEDGE);

            destination = session.createQueue(queue_name);

            messageProducer = session.createProducer(destination);

            for (int i=0;i<=5;i++) {
                TextMessage textMessage = session.createTextMessage(msg+i);
                messageProducer.send(textMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != messageProducer) {
                    messageProducer.close();
                }
                if (null != session) {
                    session.close();
                }
                if (null != connection) {
                    connection.close();
                }
            }catch (Exception e){

            }
        }
    }
}
