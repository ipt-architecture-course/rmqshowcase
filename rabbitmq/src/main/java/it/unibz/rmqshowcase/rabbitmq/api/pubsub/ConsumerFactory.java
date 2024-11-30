package it.unibz.rmqshowcase.rabbitmq.api.pubsub;

import com.rabbitmq.client.DeliverCallback;
import it.unibz.rmqshowcase.rabbitmq.Consumer;

public interface ConsumerFactory {
    Consumer createConsumer(DeliverCallback callback);
}
