package exercise.controller.service;

import exercise.rabbit.QueueListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    private final QueueListener listener;

    @Autowired
    public MessageServiceImpl(QueueListener listener) {
        this.listener = listener;
    }

    @Override
    public List<String> getAllMessages() {
        return listener.getAllMessages();
    }
}
