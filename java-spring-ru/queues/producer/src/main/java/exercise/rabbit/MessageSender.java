package exercise.rabbit;

import exercise.rabbit.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class MessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);
    private final RabbitTemplate rabbitTemplate;

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        try {
            rabbitTemplate.convertAndSend(RabbitMqConfig.RABBIT_TOPIC_USERS,  RabbitMqConfig.RABBIT_KEY, message);
            LOGGER.info("message has been sent successfully");
        } catch (Exception e) {
            LOGGER.error("message has not been sent", e);
        }
    }
}
