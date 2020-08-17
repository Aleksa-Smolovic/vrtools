package com.vrtools.me.service;

import com.vrtools.me.domain.User;
import com.vrtools.me.repository.UserRepository;
import com.vrtools.me.service.dto.MessageDTO;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class WebsocketService {

    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;

    public WebsocketService(SimpMessageSendingOperations messagingTemplate, UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
    }

    public void sendNotification(String text){
        messagingTemplate.convertAndSend("/topic/notifications", text);
    }

    public void sendObjectNotification(MessageDTO notification){
        messagingTemplate.convertAndSend("/topic/notifications/object", notification);
    }

    public void sendMessageToUser(MessageDTO message){
        User user = userRepository.findOneByLogin("admin").orElse(null);
        assert(user != null);
        messagingTemplate.convertAndSendToUser(user.getLogin(), "/queue/chat", message);
    }

}
