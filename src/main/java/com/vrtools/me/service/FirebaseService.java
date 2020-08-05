package com.vrtools.me.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.TopicManagementResponse;
import com.vrtools.me.service.util.HeaderRequestInterceptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The type Firebase service.
 */
@Service
public class FirebaseService {

    private final Logger log = LoggerFactory.getLogger(FirebaseService.class);

    @Value("${firebase.server-key}")
    private String FIREBASE_SERVER_KEY;

    @Value("${firebase.api-url}")
    private String FIREBASE_API_URL;

    @Autowired
    private FirebaseApp firebaseApp;

    /**
     * @param entity request entity being sent
     * @return CompletableFuture<String> sent message status
     */
    private CompletableFuture<String> send(HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);
        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
        return CompletableFuture.completedFuture(firebaseResponse);
    }

    private boolean send(JSONObject notificationBody) {
        // Additional meta-data, ie. click event, icon, sound..
        notificationBody.put("priority", "high");
        HttpEntity<String> request = new HttpEntity<>(notificationBody.toString());
        CompletableFuture<String> pushNotification = send(request);
        CompletableFuture.allOf(pushNotification).join();
        try {
            String firebaseResponse = pushNotification.get();
            log.info("Firebase response: " + firebaseResponse);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            log.error("Firebase error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Create notification body from title and text body
     *
     * @param notificationTitle the notification title
     * @param notificationBody  the notification body
     * @return CompletableFuture<String> sent message status
     */
    private JSONObject createNotificationBody(String notificationTitle, String notificationBody) {
        JSONObject body = new JSONObject();
        // FCM automatically displays the message to end-user devices on behalf of the client app. Notification messages have a predefined set of user-visible keys and an optional data payload of custom key-value pairs.
        JSONObject notification = new JSONObject();
        notification.put("title", notificationTitle);
        notification.put("body", notificationBody);
        body.put("notification", notification);
        return body;
    }

    private JSONObject createCustomNotification(Object object, JSONObject notificationBody) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(object);
            // Client app is responsible for processing data messages. Data messages have only custom key-value pairs with no reserved key names (see below).
            return notificationBody.put("data", new JSONObject(jsonString));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * Send to topic boolean.
     *
     * @param notificationTitle the notification title
     * @param notificationBody  the notification body
     * @param topic             the topic to which notification is being sent
     * @return the boolean if notification has been sent
     */
    public boolean sendToTopic(String notificationTitle, String notificationBody, String topic) {
        JSONObject body = createNotificationBody(notificationTitle, notificationBody);
        body.put("to", "/topics/" + topic);
        return send(body);
    }

    /**
     * Send notification by token.
     *
     * @param notificationTitle the notification title
     * @param notificationBody  the notification body
     * @param token             the token to which notification is being sent
     * @return the boolean if notification has been sent
     */
    public boolean sendToToken(String notificationTitle, String notificationBody, String token) {
        JSONObject body = createNotificationBody(notificationTitle, notificationBody);
        body.put("to", "/token/" + token);
        return send(body);
    }

    /**
     * Send to topic boolean.
     *
     * @param notificationTitle the notification title
     * @param notificationBody  the notification body
     * @param object            the object
     * @param topic             the topic
     * @return the boolean if notification has been sent
     */
    public boolean sendToTopic(String notificationTitle, String notificationBody, Object object, String topic) {
        JSONObject body = createNotificationBody(notificationTitle, notificationBody);
        body.put("to", "/topics/" + topic);
        createCustomNotification(object, body);
        return send(body);
    }

    /**
     * Send to token boolean.
     *
     * @param notificationTitle the notification title
     * @param notificationBody  the notification body
     * @param object            the object
     * @param token             the token
     * @return the boolean if notification has been sent
     */
    public boolean sendToToken(String notificationTitle, String notificationBody, Object object, String token) {
        JSONObject body = createNotificationBody(notificationTitle, notificationBody);
        body.put("to", "/token/" + token);
        createCustomNotification(object, body);
        return send(body);
    }

    /**
     * Subscribe client to topic.
     *
     * @param token the client token
     * @param topic the token to which client is being subscribed to
     * @return the boolean if client has been subscribed
     */
    public boolean subscribeClientToTopic(String token, String topic) {
        List<String> clientToken = Collections.singletonList(token);
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(clientToken, topic);
            log.info("Firebase subscription success: " + response.getSuccessCount());
            return true;
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscription error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Unsubscribe client from topic.
     *
     * @param token the client token
     * @param topic the token from which client is being unsubscribed
     * @return the boolean if client has been unsubscribed
     */
    public boolean unsubscribeClientFromTopic(String token, String topic) {
        List<String> clientToken = Collections.singletonList(token);
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(clientToken, topic);
            log.info("Firebase subscription success: " + response.getSuccessCount());
            return true;
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscription error: " + e.getMessage());
        }
        return false;
    }

}
