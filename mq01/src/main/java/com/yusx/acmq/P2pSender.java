package com.yusx.acmq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class P2pSender {

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

            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(queue_name);

            messageProducer = session.createProducer(destination);

            TextMessage textMessage = session.createTextMessage(msg);

            messageProducer.send(textMessage);

            System.in.read();
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
