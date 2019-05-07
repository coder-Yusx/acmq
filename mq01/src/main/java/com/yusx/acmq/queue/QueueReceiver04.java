package com.yusx.acmq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//延时确认
public class QueueReceiver04 {

    public static void main(String[] args) {

        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer messageConsumer = null;
        TextMessage message = null;

        String broker_url = "tcp://47.92.239.77:61616?" +
                "jms.optimizeAcknowledge=true&" +
                "jms.optimizeAcknowledgeTimeOut=1000000";
        String queue_name = "myQueue02?customer.prefetchSize=10";
        String msg = "你好啊，梦想家";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker_url);

        try {
            connection = connectionFactory.createConnection();

            //延时确认
            session=connection.createSession(Boolean.FALSE,Session.DUPS_OK_ACKNOWLEDGE);

            destination = session.createQueue(queue_name);

            messageConsumer = session.createConsumer(destination);

            connection.start();

            System.out.println("is OptimizeAck:" + ((ActiveMQConnectionFactory) connectionFactory).isOptimizeAcknowledge());
            System.out.println("OptimizeAck timeout:" + ((ActiveMQConnectionFactory) connectionFactory).getOptimizeAcknowledgeTimeOut());
            System.out.println("QueuePrefetch:" + ((ActiveMQConnectionFactory) connectionFactory).getPrefetchPolicy().getQueuePrefetch());

            for(int i=0;i<=25;i++) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                System.out.println(textMessage.getText());
                if(i%4==0) {
                    //textMessage.acknowledge();
                    Thread.sleep(3000);
                }
            }

            /*messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    String text = null;
                    try {
                        System.out.println("-------- ----- --------- ");
                        System.out.println("触发消息接收");
                        TextMessage textMessage = (TextMessage) message;
                        text = textMessage.getText();

                        System.out.println("准备处理消息"+text);
                        Thread.sleep(500);
                        System.out.println("成功处理消息 : " + text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("is OptimizeAck:" + ((ActiveMQConnectionFactory) connectionFactory).isOptimizeAcknowledge());
            System.out.println("OptimizeAck timeout:" + ((ActiveMQConnectionFactory) connectionFactory).getOptimizeAcknowledgeTimeOut());
            System.out.println("QueuePrefetch:" + ((ActiveMQConnectionFactory) connectionFactory).getPrefetchPolicy().getQueuePrefetch());*/

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
