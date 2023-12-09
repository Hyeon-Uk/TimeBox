package TIAB.timebox.service.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthUserInfo {
    private String email;
    private String nickname;
    private String socialProvider;
    private String socialId;
    private String profileImageUrl;
}
