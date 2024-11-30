package it.unibz.rmqshowcase.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionManager {
    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    public ConnectionManager() {
        this.factory = new ConnectionFactory();
    }

    public void configure(String url, String username, String password) {
        this.factory.setHost(url);
        this.factory.setUsername(username);
        this.factory.setPassword(password);
    }

    public void connect() throws IOException, TimeoutException {
        if (this.connection == null || this.connection.isOpen()) return;

        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void disconnect() throws IOException, TimeoutException {
        if (this.channel != null && this.channel.isOpen())
            this.channel.close();

        if (this.connection != null && this.connection.isOpen())
            this.connection.close();
    }
}
