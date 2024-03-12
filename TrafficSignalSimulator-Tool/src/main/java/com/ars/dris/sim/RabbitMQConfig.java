package com.ars.dris.sim;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String E1QUEUE_NAME = "EntryQueue1";
    public static final String E2QUEUE_NAME = "EntryQueue2";
    public static final String P1QUEUE_NAME = "PlatformQueue1";
    // ... declare other queue names

    public static final String EXCHANGE_NAME = "CommonExchange";

    @Bean
    Queue E1Queue() {
        return new Queue(E1QUEUE_NAME, false);
    }

    @Bean
    Queue E2Queue() {
        return new Queue(E2QUEUE_NAME, false);
    }

    @Bean
    Queue P1Queue() {
        return new Queue(P1QUEUE_NAME, false);
    }

    // ... declare other queues

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding E1Binding(Queue E1Queue, DirectExchange exchange) {
        return BindingBuilder.bind(E1Queue).to(exchange).with(E1QUEUE_NAME);
    }

    @Bean
    Binding E2Binding(Queue E2Queue, DirectExchange exchange) {
        return BindingBuilder.bind(E2Queue).to(exchange).with(E2QUEUE_NAME);
    }

    @Bean
    Binding P1Binding(Queue P1Queue, DirectExchange exchange) {
        return BindingBuilder.bind(P1Queue).to(exchange).with(P1QUEUE_NAME);
    }

    // ... declare other bindings

    @Bean
    SimpleMessageListenerContainer E1Container(ConnectionFactory connectionFactory,
                                                MessageListenerAdapter listenerAdapter) {
        return container(connectionFactory, listenerAdapter, E1QUEUE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer E2Container(ConnectionFactory connectionFactory,
                                                MessageListenerAdapter listenerAdapter) {
        return container(connectionFactory, listenerAdapter, E2QUEUE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer P1Container(ConnectionFactory connectionFactory,
                                                MessageListenerAdapter listenerAdapter) {
        return container(connectionFactory, listenerAdapter, P1QUEUE_NAME);
    }

    // ... declare other containers

    private SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                     MessageListenerAdapter listenerAdapter, String queueName) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}