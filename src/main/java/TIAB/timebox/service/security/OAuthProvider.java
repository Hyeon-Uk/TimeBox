package TIAB.timebox.service.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum OAuthProvider {
    KAKAO("kakao") {
        @Override
        public OAuthUserInfo getUserInfo(OAuth2User user) {
            Map<String, Object> attributes = user.getAttributes();
            Map<String, Object> properties = user.getAttribute("properties");
            Map<String, Object> account = user.getAttribute("kakao_account");


            return OAuthUserInfo.builder()
                    .socialProvider(KAKAO.provider)
                    .socialId(String.valueOf(attributes.get("id")))
                    .email(String.valueOf(account.get("email")))
                    .nickname(String.valueOf(properties.get("nickname")))
                    .profileImageUrl(String.valueOf(properties.get("profile_image")))
                    .build();
        }
    };

    private final String provider;
    private static final Map<String, OAuthProvider> PROVIDERS =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(OAuthProvider::getProvider, Function.identity())));

    public static OAuthProvider getOAuthProviderByName(String providerName) {
        if (!PROVIDERS.containsKey(providerName)) {
            throw new IllegalArgumentException("지원하지 않는 로그인입니다.");
        }
        return PROVIDERS.get(providerName);
    }

    public abstract OAuthUserInfo getUserInfo(OAuth2User user);
}
