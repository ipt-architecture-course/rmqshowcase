package it.unibz.rmqshowcase.topicproducer;

import it.unibz.rmqshowcase.rabbitmq.ChannelConfigurer;
import it.unibz.rmqshowcase.rabbitmq.ConnectionManager;
import it.unibz.rmqshowcase.rabbitmq.Producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicPublisher {
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.configure("localhost", "admin", "admin");
        connectionManager.connect();

        ChannelConfigurer channelConfigurer = new ChannelConfigurer(connectionManager);
        channelConfigurer.declareTopic(EXCHANGE_NAME);
        Producer producer = channelConfigurer.createProducer();

        String routingKey = "task.urgent";
        String message = "This is an urgent task";

        producer.send(message, routingKey);
        System.out.printf("Published message: '%s' with routing key '%s'%n", message, routingKey);

        connectionManager.disconnect();
    }
}
