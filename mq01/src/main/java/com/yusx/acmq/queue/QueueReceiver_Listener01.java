package com.yusx.acmq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
//自动确认+消息监听
public class QueueReceiver_Listener01 {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer messageConsumer = null;
        Message message = null;


        String broker_url = "tcp://47.92.239.77:61616";
        String queue_name = "myQueue";

        try {
            connectionFactory = new ActiveMQConnectionFactory(broker_url);
            connection = connectionFactory.createConnection();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queue_name);
            messageConsumer = session.createConsumer(destination);
            connection.start();
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println("接收到的消息为"+((TextMessage)message).getText());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭连接释放资源
                if (null != messageConsumer) {
                    messageConsumer.close();
                }
                if (null != session) {
                    session.close();
                }
                if (null != connection) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
