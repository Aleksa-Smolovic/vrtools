package com.vrtools.me.web.rest;

import com.vrtools.me.service.WebsocketService;
import com.vrtools.me.service.dto.NotificationDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebsocketController {

    private final WebsocketService websocketService;

    public WebsocketController(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    @MessageMapping("/send-message")
    public void sendMessageToUser(String text, String userToken){
        websocketService.sendNotificationToUser(userToken, text);
    }

    @PostMapping("/send-notification")
    public void sendNotification(String topic, NotificationDTO notificationDTO){
        websocketService.sendNotificationToTopic(topic, notificationDTO);
    }

}
