package exercise.rabbit;

import exercise.rabbit.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;


@Component
public class QueueListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueListener.class);
    private final List<String> messages = new ArrayList<>();

    @RabbitListener(queues = RabbitMqConfig.RABBIT_QUEUE_NAME)
    public void process(String message) {
        try {
            LOGGER.info("message read successfully");
            messages.add(message);
        } catch (Exception e) {
            LOGGER.error("couldn't read the message", e);
            throw new RuntimeException(e);
        }
    }
    public List<String> getAllMessages() {
        return messages;
    }
}
