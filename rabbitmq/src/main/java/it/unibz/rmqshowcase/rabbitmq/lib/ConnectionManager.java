package it.unibz.rmqshowcase.rabbitmq.lib;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import it.unibz.rmqshowcase.rabbitmq.RemoteServerConnectionConfig;
import it.unibz.rmqshowcase.rabbitmq.api.connection.ChannelProvider;
import it.unibz.rmqshowcase.rabbitmq.api.connection.Configurable;
import it.unibz.rmqshowcase.rabbitmq.api.connection.Connector;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionManager implements Connector, Configurable<RemoteServerConnectionConfig>, ChannelProvider {
    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public ConnectionManager() {
        this.factory = new ConnectionFactory();
    }

    @Override
    public void configure(RemoteServerConnectionConfig credentials) {
        this.factory.setHost(credentials.url());
        this.factory.setUsername(credentials.username());
        this.factory.setPassword(credentials.password());
    }

    @Override
    public void connect() throws IOException, TimeoutException {
        if (this.connection == null || this.connection.isOpen()) return;

        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public void disconnect() throws IOException, TimeoutException {
        if (this.channel != null && this.channel.isOpen())
            this.channel.close();

        if (this.connection != null && this.connection.isOpen())
            this.connection.close();
    }
}
