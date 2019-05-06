package com.yusx.acmq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//客户端手动确认
public class QueueReceiver02 {

    public static void main(String[] args) {

        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer messageConsumer = null;
        TextMessage message = null;

        String broker_url = "tcp://47.92.239.77:61616";
        String queue_name = "myQueue";
        String msg = "你好啊，梦想家";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker_url);

        try {
            connection = connectionFactory.createConnection();

            session=connection.createSession(Boolean.FALSE,Session.CLIENT_ACKNOWLEDGE);

            destination = session.createQueue(queue_name);

            messageConsumer = session.createConsumer(destination);

            connection.start();

            message = (TextMessage) messageConsumer.receive();
            System.out.println("收到消息" + message.getText());

            //手动确认，如果不确认broker中的消息不会被删除，可以一直消费
            message.acknowledge();

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
