package it.unibz.rmqshowcase.rabbitmq.api.connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Connector {
    void connect() throws IOException, TimeoutException;

    void disconnect() throws IOException, TimeoutException;
}
