package com.yusx.acmq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PersistentTopicSender {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageProducer messageProducer = null;
        TextMessage textMessage;

        String broker_url = "tcp://47.92.239.77:61616";
        String topic_name = "myTopic";
        String msg = "有梦想谁都了不起";

        try {
            connectionFactory = new ActiveMQConnectionFactory(broker_url);
            connection = connectionFactory.createConnection();
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);

            destination = session.createTopic(topic_name);
            messageProducer = session.createProducer(destination);
            //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

            connection.start();
            textMessage=session.createTextMessage(msg);

            messageProducer.send(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                messageProducer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
