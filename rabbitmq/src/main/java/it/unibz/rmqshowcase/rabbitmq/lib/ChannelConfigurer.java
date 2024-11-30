package it.unibz.rmqshowcase.rabbitmq.lib;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import it.unibz.rmqshowcase.rabbitmq.Consumer;
import it.unibz.rmqshowcase.rabbitmq.Producer;
import it.unibz.rmqshowcase.rabbitmq.api.channel.ChannelManipulator;
import it.unibz.rmqshowcase.rabbitmq.api.pubsub.ConsumerFactory;
import it.unibz.rmqshowcase.rabbitmq.api.pubsub.ProducerFactory;

import java.io.IOException;

public class ChannelConfigurer implements ChannelManipulator, ProducerFactory, ConsumerFactory {
    private final ConnectionManager connectionManager;
    private Channel channel;
    private String exchangeName;
    private String queueName;

    public ChannelConfigurer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void declareTopic(String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        this.channel = this.connectionManager.getChannel();
        this.channel.exchangeDeclare(this.exchangeName, BuiltinExchangeType.TOPIC);
    }

    @Override
    public void bindDurableQueue(String name, String bindingKey) throws IOException {
        this.queueName = name;
        this.channel.queueDeclare(this.queueName, true, false, false, null);
        this.channel.queueBind(this.queueName, this.exchangeName, bindingKey);
    }

    @Override
    public Producer createProducer() {
        return new Producer(this.channel, this.exchangeName);
    }

    @Override
    public Consumer createConsumer(DeliverCallback callback) {
        return new Consumer(this.channel, this.queueName, callback);
    }
}
