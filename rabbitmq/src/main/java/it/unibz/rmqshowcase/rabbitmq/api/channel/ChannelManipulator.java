package it.unibz.rmqshowcase.rabbitmq.api.channel;

import java.io.IOException;

public interface ChannelManipulator {
    void declareTopic(String exchangeName) throws IOException;

    void bindDurableQueue(String name, String bindingKey) throws IOException;
}
