package com.gerasimchuk.rabbit;

/**
 * The interface Rabbit mq sender.
 */
public interface RabbitMQSender {
    /**
     * Send message.
     *
     * @param message the message
     */
    void sendMessage(String message);
}
