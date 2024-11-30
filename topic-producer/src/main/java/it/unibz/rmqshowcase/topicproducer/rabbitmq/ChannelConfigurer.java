package it.unibz.rmqshowcase.topicproducer.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class ChannelConfigurer {
    private final ConnectionManager connectionManager;
    private Channel channel;
    private String exchangeName;

    public ChannelConfigurer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void declareTopic(String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        this.channel = this.connectionManager.getChannel();
        this.channel.exchangeDeclare(this.exchangeName, BuiltinExchangeType.TOPIC);
    }

    public Producer createProducer() {
        return new Producer(this.channel, this.exchangeName);
    }
}
