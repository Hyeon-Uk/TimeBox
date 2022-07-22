package TIAB.timebox.service.security;

import TIAB.timebox.dto.UserDtoReq;
import TIAB.timebox.dto.UserDtoRes;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.service.user.UserServiceImpl;
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
import java.util.Optional;

@Slf4j
@Service
public class KakaoOAuth2UserService extends DefaultOAuth2UserService{

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        KakaoProfile profile=objectMapper.convertValue(attributes,KakaoProfile.class);
        long profileKakaoId=profile.getId();
        String profileEmail=profile.getKakao_account().getEmail();
        String profileImgSrc=profile.getKakao_account().getProfile().getProfile_image_url();
//        Optional<User> userOptional=userService.findByKakaoId(profile.getId());
//        User user;
//        if(userOptional.isPresent()) {
//            user=userOptional.get();
//            user.setKakaoId(profile.getId());
//            user.setEmail(profile.getKakao_account().getEmail());
//            user.setImgSrc(profile.getKakao_account().getProfile().getProfile_image_url());
//        }
//        else{
//            user=User.builder()
//                    .kakaoId(profile.getId())
//                    .email(profile.getKakao_account().getEmail())
//                    .imgSrc(profile.getKakao_account().getProfile().getProfile_image_url())
//                    .build();
//        }
//        User savedUser=userService.save(user);
        UserDtoReq dtoReq=UserDtoReq.builder()
                .kakaoId(profileKakaoId)
                .email(profileEmail)
                .imgSrc(profileImgSrc)
                .build();
        UserDtoRes savedUser=userService.save(dtoReq);

        httpSession.setAttribute("id",savedUser.getId());
        httpSession.setAttribute("kakao_id",savedUser.getKakaoId());
        httpSession.setAttribute("email",savedUser.getEmail());


        return new DefaultOAuth2User( Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id" );
    }
}