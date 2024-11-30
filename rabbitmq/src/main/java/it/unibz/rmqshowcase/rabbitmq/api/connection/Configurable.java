package it.unibz.rmqshowcase.rabbitmq.api.connection;

public interface Configurable <T> {
    void configure(T credentials);
}
