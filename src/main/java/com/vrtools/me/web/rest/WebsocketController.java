package com.vrtools.me.web.rest;

import com.vrtools.me.service.WebsocketService;
import com.vrtools.me.service.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketController {

    private final WebsocketService websocketService;

    public WebsocketController(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }

    @PostMapping("/api/notifications")
    public void sendNotification(@RequestParam String text){
        websocketService.sendNotification(text);
    }

    @PostMapping("/api/notifications/object")
    @MessageMapping("/send/notifications/object")
    public void sendObjectNotification(@RequestBody MessageDTO notification){
        websocketService.sendObjectNotification(notification);
    }

    @PostMapping("/api/message/object")
    @MessageMapping("/send/message/object")
    public void sendMessageToUser(@RequestBody MessageDTO messageDTO){
        websocketService.sendMessageToUser(messageDTO);
    }

}
