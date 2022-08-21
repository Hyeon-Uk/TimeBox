package TIAB.timebox.service.security;

import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.UserRepository;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class KakaoOAuth2UserService extends DefaultOAuth2UserService{
    @Autowired
    private UserRepository userRepository;

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

//        UserDtoReq dtoReq=UserDtoReq.builder()
//                .kakaoId(profileKakaoId)
//                .email(profileEmail)
//                .imgSrc(profileImgSrc)
//                .build();
//        UserDtoRes savedUser=userService.save(dtoReq);
//
//        Map<String,Object> verifyInfo=new HashMap<>();
//        verifyInfo.put("id",savedUser.getId());
        Optional<User> byKakaoId = userRepository.findByKakaoId(profileKakaoId);
        User securityUser=null;
        if(!byKakaoId.isPresent()){
            User newUser=User.builder()
                    .kakaoId(profileKakaoId)
                    .email(profileEmail)
                    .imgSrc(profileImgSrc)
                    .build();

            securityUser=userRepository.save(newUser);
        }
        else{
            securityUser=byKakaoId.get();
        }
        Map<String,Object> verifyInfo=new HashMap<>();
        verifyInfo.put("id",securityUser.getId());

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),verifyInfo,"id");
    }
}