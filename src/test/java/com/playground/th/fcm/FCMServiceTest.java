package com.playground.th.fcm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FCMServiceTest {
    @Test
    void sendTest() throws Exception {
        String token ="emL4KS9lRaCVUmHksjaxg2:APA91bHqJTNJ-wCukgcPv8qZVMVcdHZg4_RmlvfVnYRdtBewpfEmFAC-sF3AUhebzD_U6Y1sDoiJ2F9Vy1oHzd3bdXgiUwkVTEgS5Th7Cq6ym8CN4nE8IFlIEZ3S7ZBUX7VbsmqTbQNn";
        // FCMMessage()
        FCMMessage fcmMessage = new FCMMessage(token,"안녕","첫 푸시알림");
        new FCMService().sendToMember(fcmMessage);
    }

}