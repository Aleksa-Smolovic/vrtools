package com.vrtools.me.service;

import com.vrtools.me.service.dto.NotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebsocketService {

    private final Logger log = LoggerFactory.getLogger(WebsocketService.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(String token, String message){
        messagingTemplate.convertAndSend("/user/" + token, message);
    }

    public void sendNotificationToTopic(String topic, NotificationDTO notificationDTO){
        messagingTemplate.convertAndSend("/topic/" + topic, notificationDTO);
    }

}
