//package com.gerasimchuk.rabbit;
//
//import com.rabbitmq.client.*;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class RabbitMQReceiverImpl implements RabbitMQReceiver {
//
//    private final static String QUEUE_NAME = "hello";
//
//    private ConnectionFactory factory;
//    private Connection connection;
//    private Channel channel;
//    private void init(){
//        factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        try {
//            connection = factory.newConnection();
//            channel = connection.createChannel();
//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//
//            Consumer consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
//                        throws IOException {
//                    String message = new String(body, "UTF-8");
//                    System.out.println(" [x] Received '" + message + "'");
//                }
//            };
//        channel.basicConsume(QUEUE_NAME, true, consumer);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public RabbitMQReceiverImpl() {
//        init();
//    }
//}
