package com.playground.th.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class FCMInitializer {
    public static final String FIRE_BASE_SERVER_KEY = "AAAAP2Z7Cak:APA91bEbBhN8CJ8-u9MF5K5J-C-IWrnY9lXLTLDsuY_0M-JGPTS9Z7ZOIwhOMjpK_DHVh9gkGnrAsXatxO1J1HlqeFg1MWIY4Zi45ukhJvc4MXJt69lPJFSI2WnHk7yJoQnr4C__9ujV";
    public static final String FIRE_BASE_SEND_PATH = "https://fcm.googleapis.com/fcm/send";

}