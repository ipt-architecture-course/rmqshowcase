package it.unibz.rmqshowcase.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Consumer {
    private final Channel channel;
    private final String queueName;
    private final DeliverCallback callback;

    public Consumer(Channel channel, String queueName, DeliverCallback callback) {
        this.channel = channel;
        this.queueName = queueName;
        this.callback = callback;
    }

    public void run() throws IOException {
        this.channel.basicConsume(this.queueName, true, this.callback, consumerTag -> {});
    }
}
