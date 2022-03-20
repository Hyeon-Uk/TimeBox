package TIAB.timebox.dto;

import com.google.firebase.messaging.WebpushConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FcmMessage {
    private String token;
    private WebpushConfig webpushConfig;

}
