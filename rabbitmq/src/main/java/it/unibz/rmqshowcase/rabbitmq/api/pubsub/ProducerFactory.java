package it.unibz.rmqshowcase.rabbitmq.api.pubsub;

import it.unibz.rmqshowcase.rabbitmq.Producer;

public interface ProducerFactory {
    Producer createProducer();
}
