package com.yusx.acmq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class P2pReceiver {

    public static void main(String[] args) {

        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer messageConsumer = null;
        Message message = null;

                String broker_url = "tcp://47.92.239.77:61616";
        String queue_name = "myQueue";
        String msg = "你好啊，梦想家";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker_url);

        try {
            connection = connectionFactory.createConnection();

            session=connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(queue_name);

            messageConsumer = session.createConsumer(destination);

            connection.start();

            message = messageConsumer.receive();

            //判断消息的类型
            if (message instanceof TextMessage) { //判断是否是文本消息
                String text = ((TextMessage) message).getText();
                System.out.println("接收到的消息内容是：" + text);
            }

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
