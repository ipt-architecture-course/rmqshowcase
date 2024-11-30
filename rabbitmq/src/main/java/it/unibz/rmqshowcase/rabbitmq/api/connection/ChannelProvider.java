package it.unibz.rmqshowcase.rabbitmq.api.connection;

import com.rabbitmq.client.Channel;

public interface ChannelProvider {
    Channel getChannel();
}
