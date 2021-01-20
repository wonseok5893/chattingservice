package com.playground.th.fcm;


import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import okhttp3.internal.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class FCMService {
    private ObjectMapper objectMapper = new ObjectMapper();

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public void sendToMember(final FCMMessage fcmMessage) throws Exception {
        String message = objectMapper.writeValueAsString(fcmMessage);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,JSON);

        Request request = new Request.Builder()
                .url(FCMInitializer.FIRE_BASE_SEND_PATH)
                .post(requestBody)
                .addHeader("Authorization","key="+FCMInitializer.FIRE_BASE_SERVER_KEY)
                .addHeader("Content-Type","application/json; UTF-8")
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            log.info("메시지를 성공적으로 보냈습니다");
            log.info(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("보내는데 길패했습니다");
        }

    }

}