package com.vrtools.me.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseInitialization {

    private final Logger log = LoggerFactory.getLogger(FirebaseInitialization.class);

    @Value("${firebase.config}")
    private String firebaseConfig;

    @Bean
    public FirebaseApp initializeFirebaseInstance() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();
            return FirebaseApp.getApps().isEmpty() ? FirebaseApp.initializeApp(options) : FirebaseApp.getInstance();
        } catch (IOException e) {
            log.error("Create FirebaseApp Error", e);
        }
        return null;
    }

}
