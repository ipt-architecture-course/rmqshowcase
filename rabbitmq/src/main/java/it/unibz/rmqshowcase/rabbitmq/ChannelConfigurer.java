package it.unibz.rmqshowcase.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class ChannelConfigurer {
    private final ConnectionManager connectionManager;
    private Channel channel;
    private String exchangeName;
    private String queueName;

    public ChannelConfigurer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void declareTopic(String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        this.channel = this.connectionManager.getChannel();
        this.channel.exchangeDeclare(this.exchangeName, BuiltinExchangeType.TOPIC);
    }

    public void bindDurableQueue(String name, String bindingKey) throws IOException {
        this.queueName = name;
        this.channel.queueDeclare(this.queueName, true, false, false, null);
        this.channel.queueBind(this.queueName, this.exchangeName, bindingKey);
    }

    public Producer createProducer() {
        return new Producer(this.channel, this.exchangeName);
    }

    public Consumer createConsumer(DeliverCallback callback) {
        return new Consumer(this.channel, this.queueName, callback);
    }
}
