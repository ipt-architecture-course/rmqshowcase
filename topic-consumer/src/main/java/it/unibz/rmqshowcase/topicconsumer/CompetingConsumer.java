package it.unibz.rmqshowcase.topicconsumer;

import com.rabbitmq.client.*;
import it.unibz.rmqshowcase.rabbitmq.ChannelConfigurer;
import it.unibz.rmqshowcase.rabbitmq.ConnectionManager;
import it.unibz.rmqshowcase.rabbitmq.Consumer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class CompetingConsumer {
    private static final String EXCHANGE_NAME = "topic_exchange";
    private static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.configure("localhost", "admin", "admin");
        connectionManager.connect();

        String bindingKey = "task.#";
        ChannelConfigurer channelConfigurer = new ChannelConfigurer(connectionManager);
        channelConfigurer.declareTopic(EXCHANGE_NAME);
        channelConfigurer.bindDurableQueue(QUEUE_NAME, bindingKey);

        System.out.println("Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.printf("Received message: '%s' with routing key '%s'%n", message, delivery.getEnvelope().getRoutingKey());
        };
        Consumer consumer = channelConfigurer.createConsumer(deliverCallback);
        consumer.run();
    }
}
