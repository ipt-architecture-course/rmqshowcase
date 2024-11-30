package it.unibz.rmqshowcase.topicproducer.rabbitmq;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Producer {
    private final Channel channel;
    private final String exchangeName;

    public Producer(Channel channel, String exchangeName) {
        this.channel = channel;
        this.exchangeName = exchangeName;
    }

    public void send(String message, String routingKey) throws IOException {
        byte[] msg = message.getBytes(StandardCharsets.UTF_8);
        this.channel.basicPublish(this.exchangeName, routingKey, null, msg);
    }
}
