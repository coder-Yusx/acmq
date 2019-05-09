package com.yusx.acmq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//延时确认
public class QueueSender04 {
    public static void main(String[] args) {

        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageProducer messageProducer = null;

        String broker_url = "tcp://47.92.239.77:61616?" +
                "jms.optimizeAcknowledge=true&" +
                "jms.optimizeAcknowledgeTimeOut=1000000";
        String queue_name = "myQueue02?customer.prefetchSize=10";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker_url);

        try {

            connection = connectionFactory.createConnection();

            //延时确认
            session = connection.createSession(Boolean.FALSE,Session.DUPS_OK_ACKNOWLEDGE);

            destination = session.createQueue(queue_name);

            messageProducer = session.createProducer(destination);

            SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
            for(int i=0;i<=25;i++){
                String date = sf.format(new Date());
                String message = String.format("msg[%s][%s]", i, date);

                TextMessage textMessage = session.createTextMessage(message);

                messageProducer.send(textMessage);
                System.out.println("消息发送成功"+message);
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
