package TIAB.timebox.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class FirebaseConfig {

    @Value("/timebox-f5493-firebase-adminsdk-r0gsf-409c76f1a6.json")
    private String firebaseSdkPath;

    @PostConstruct
    public void initialize(){
        try{
            ClassPathResource resource=new ClassPathResource(firebaseSdkPath);
            InputStream serviceAccount=resource.getInputStream();

            FirebaseOptions options= FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }catch(FileNotFoundException e){
            log.warn("[Firebase] File Not Found");
        } catch (IOException e) {
            log.warn("[Firebase] IOException");
        }
    }
}