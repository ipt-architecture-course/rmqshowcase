package it.unibz.rmqshowcase.topicproducer;

import it.unibz.rmqshowcase.rabbitmq.RabbitMQ;
import it.unibz.rmqshowcase.rabbitmq.RemoteServerConnectionConfig;
import it.unibz.rmqshowcase.rabbitmq.Producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicPublisher {
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        RemoteServerConnectionConfig config = new RemoteServerConnectionConfig("localhost", "admin", "admin");

        RabbitMQ rabbitMQ = RabbitMQ.get();

        rabbitMQ.configure(config);
        rabbitMQ.connect();
        rabbitMQ.declareTopic(EXCHANGE_NAME);
        Producer producer = rabbitMQ.createProducer();

        String routingKey = "task.urgent";
        String message = "This is an urgent task";

        producer.send(message, routingKey);
        System.out.printf("Published message: '%s' with routing key '%s'%n", message, routingKey);

        rabbitMQ.disconnect();
    }
}
