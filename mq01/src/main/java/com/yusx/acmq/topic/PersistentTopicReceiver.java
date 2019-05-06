package com.yusx.acmq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PersistentTopicReceiver {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Topic topic = null;
        MessageConsumer messageConsumer = null;
        TextMessage textMessage = null;

        String broker_url = "tcp://47.92.239.77:61616";
        String topic_name = "myTopic";
        String msg = "有梦想谁都了不起";

        String Client_Id = "Yusx-001";

        try {
            connectionFactory = new ActiveMQConnectionFactory(broker_url);
            connection = connectionFactory.createConnection();
            connection.setClientID(Client_Id);
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);

            topic = session.createTopic(topic_name);
            messageConsumer = session.createDurableSubscriber(topic,Client_Id);

            connection.start();

            for (int i=0;i<=5;i++) {
                textMessage = (TextMessage)messageConsumer.receive();
                System.out.println(textMessage.getText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                messageConsumer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
