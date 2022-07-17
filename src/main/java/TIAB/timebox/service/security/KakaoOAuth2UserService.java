package TIAB.timebox.service.security;

import TIAB.timebox.entity.user.User;
import TIAB.timebox.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class KakaoOAuth2UserService extends DefaultOAuth2UserService{

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        KakaoProfile profile=objectMapper.convertValue(attributes,KakaoProfile.class);
        User user=new User();
        user.setKakaoId(profile.getId());
        user.setEmail(profile.getKakao_account().getEmail());
        user.setImgSrc(profile.getKakao_account().getProfile().getProfile_image_url());

        User savedUser=userService.save(user);

        httpSession.setAttribute("id",savedUser.getId());
        httpSession.setAttribute("kakao_id",savedUser.getKakaoId());
        httpSession.setAttribute("email",savedUser.getEmail());


        return new DefaultOAuth2User( Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id" );
    }
}