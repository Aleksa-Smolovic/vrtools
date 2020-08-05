package com.vrtools.me.web.rest;

import com.vrtools.me.service.FirebaseService;
import com.vrtools.me.service.dto.NotificationDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseResource {

    private final FirebaseService firebaseService;

    public FirebaseResource(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/topic")
    public boolean sendToTopic(@RequestParam String title, @RequestParam String body, @RequestParam String topic) {
        return firebaseService.sendToTopic(title, body, topic);
    }

    @PostMapping("/token")
    public boolean sendToToken(@RequestParam String title, @RequestParam String body, @RequestParam String token) {
        return firebaseService.sendToToken(title, body, token);
    }

    @PostMapping("/custom/topic")
    public boolean sendCustomToTopic(@RequestParam String title, @RequestParam String body, @Valid @RequestBody NotificationDTO notificationDTO, @RequestParam String topic) {
        return firebaseService.sendToTopic(title, body, notificationDTO, topic);
    }

    @PostMapping("/custom/token")
    public boolean sendCustomToToken(@RequestParam String title, @RequestParam String body, @Valid @RequestBody NotificationDTO notificationDTO, @RequestParam String token) {
        return firebaseService.sendToToken(title, body, notificationDTO, token);
    }

    @PostMapping("/subscribe")
    public boolean subscribeToTopic(@RequestParam String token, @RequestParam String topic) {
        return firebaseService.subscribeClientToTopic(token, topic);
    }

    @PostMapping("/unsubscribe")
    public boolean unsubscribeFromTopic(@RequestParam String token, @RequestParam String topic) {
        return firebaseService.unsubscribeClientFromTopic(token, topic);
    }

}
