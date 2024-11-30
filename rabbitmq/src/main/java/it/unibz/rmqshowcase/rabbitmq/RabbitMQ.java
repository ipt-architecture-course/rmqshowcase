package it.unibz.rmqshowcase.rabbitmq;

import com.rabbitmq.client.DeliverCallback;
import it.unibz.rmqshowcase.rabbitmq.api.channel.ChannelManipulator;
import it.unibz.rmqshowcase.rabbitmq.api.connection.Configurable;
import it.unibz.rmqshowcase.rabbitmq.api.connection.Connector;
import it.unibz.rmqshowcase.rabbitmq.api.pubsub.ConsumerFactory;
import it.unibz.rmqshowcase.rabbitmq.api.pubsub.ProducerFactory;
import it.unibz.rmqshowcase.rabbitmq.lib.ChannelConfigurer;
import it.unibz.rmqshowcase.rabbitmq.lib.ConnectionManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQ implements
        Configurable<RemoteServerConnectionConfig>,
        Connector,
        ChannelManipulator,
        ProducerFactory,
        ConsumerFactory
{
    private ConnectionManager connectionManager = new ConnectionManager();
    private ChannelConfigurer channelConfigurer;

    public static RabbitMQ get() {
        return new RabbitMQ();
    }

    private RabbitMQ() {}

    @Override
    public void configure(RemoteServerConnectionConfig credentials) {
        this.connectionManager.configure(credentials);
    }

    @Override
    public void connect() throws IOException, TimeoutException {
        this.connectionManager.connect();
        this.channelConfigurer = new ChannelConfigurer(this.connectionManager);
    }

    @Override
    public void disconnect() throws IOException, TimeoutException {
        this.connectionManager.disconnect();
        this.channelConfigurer = null;
    }


    @Override
    public void declareTopic(String exchangeName) throws IOException {
        this.channelConfigurer.declareTopic(exchangeName);
    }

    @Override
    public void bindDurableQueue(String name, String bindingKey) throws IOException {
        this.channelConfigurer.bindDurableQueue(name, bindingKey);
    }

    @Override
    public Consumer createConsumer(DeliverCallback callback) {
        return this.channelConfigurer.createConsumer(callback);
    }

    @Override
    public Producer createProducer() {
        return this.channelConfigurer.createProducer();
    }
}
