package TIAB.timebox.service.security;

import TIAB.timebox.entity.member.Member;
import TIAB.timebox.service.member.MemberOAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberOAuthService memberOAuthService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String providerName = userRequest.getClientRegistration().getRegistrationId();

        OAuthUserInfo userInfo = OAuthProvider.getOAuthProviderByName(providerName)
                .getUserInfo(oAuth2User);

        Member member = memberOAuthService.getOrRegistWithOAuthInfo(userInfo);
        return new CustomOAuth2User(member, oAuth2User.getAttributes());
    }
}
