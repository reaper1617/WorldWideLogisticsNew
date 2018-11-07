//package com.gerasimchuk.rabbit;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@EnableRabbit
//@Component
//public class RabbitMQListener {
//
//    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(RabbitMQListener.class);
//
//    @RabbitListener(queues = "myQueue")
//    public void onReceive(Message msg){
//        LOGGER.info("Received from myQueue:" + new String(msg.getBody()));
//    }
//
//}
