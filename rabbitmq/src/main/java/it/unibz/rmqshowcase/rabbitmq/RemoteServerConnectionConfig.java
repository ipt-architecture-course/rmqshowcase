package it.unibz.rmqshowcase.rabbitmq;

public record RemoteServerConnectionConfig(
        String url,
        String username,
        String password
) {
}
