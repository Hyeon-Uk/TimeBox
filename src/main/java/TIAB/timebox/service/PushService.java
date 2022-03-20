package TIAB.timebox.service;

import TIAB.timebox.dto.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PushService {
    private final String API_URL="https://fcm.googleapis.com/v1/projects/timebox-f5493/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(String targetToken,String title,String body) throws IOException {
        String message=makeMessage(targetToken,title,body);
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody= RequestBody.create(message, MediaType.get("application/json;charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION,"Bearer "+getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE,"application/json;UTF-8")
                .build();

        Response response=client.newCall(request)
                .execute();
    }

    private String makeMessage(String targetToken,String title,String body) throws JsonProcessingException {
        FcmMessage fcmMessage= FcmMessage.builder()
                .token(targetToken)
                .webpushConfig(WebpushConfig.builder()
                        .putHeader("ttl","300")
                        .setNotification(WebpushNotification.builder()
                        .setTitle(title)
                        .setBody(body).build())
                        .build())
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath="timebox-f5493-firebase-adminsdk-r0gsf-409c76f1a6.json";

        GoogleCredentials googleCredentials=GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
