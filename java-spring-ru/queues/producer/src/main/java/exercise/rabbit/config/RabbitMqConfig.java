package exercise.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String RABBIT_QUEUE_NAME = "queue";
    public static final String RABBIT_TOPIC_USERS = "exchange";
    public static final String RABBIT_KEY = "key";

    @Bean
    public Queue queue() {
        return new Queue(RABBIT_QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RABBIT_TOPIC_USERS);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(RABBIT_KEY);
    }
}
