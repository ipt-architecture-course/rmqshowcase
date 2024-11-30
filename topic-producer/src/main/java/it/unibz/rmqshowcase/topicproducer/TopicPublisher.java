package it.unibz.rmqshowcase.topicproducer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import it.unibz.rmqshowcase.topicproducer.rabbitmq.ConnectionManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class TopicPublisher {
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.configure("localhost", "admin", "admin");
        connectionManager.connect();
        Channel channel = connectionManager.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String routingKey = "task.urgent";
        String message = "This is an urgent task";

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.printf("Published message: '%s' with routing key '%s'%n", message, routingKey);

        connectionManager.disconnect();
    }
}
