package TIAB.timebox.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthToken {
    String token_type;
    String access_token;
    String expires_in;
    String refresh_token;
    String refresh_token_expires_in;
    String scope;
}
