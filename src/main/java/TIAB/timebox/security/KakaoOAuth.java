package TIAB.timebox.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoOAuth {
    @Value("${kakao.key}")
    private String appKey;
    @Value("${kakao.redirect}")
    private String redirect;
    @Value("${kakao.secret}")
    private String secret;

    @Autowired
    private ObjectMapper objectMapper;

    public KakaoProfile getUserInfo(String code){
        String accessToken=getAccessToken(code);
        KakaoProfile profile=getUserInfoByToken(accessToken);
        return profile;
    }

    private String getAccessToken(String code){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", appKey);
        params.add("redirect_uri", redirect);
        params.add("code", code);
        params.add("client_secret", secret);

        RestTemplate rt=new RestTemplate();
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest=new HttpEntity<>(params,headers);

        ResponseEntity<String> response=rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        OAuthToken token=null;
        try{
            token=objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return token.getAccess_token();
    }

    private KakaoProfile getUserInfoByToken(String accessToken){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt=new RestTemplate();
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest=new HttpEntity<>(headers);
        ResponseEntity<String> resourceProfileResponse=rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        KakaoProfile profile=null;
        try{
            profile=objectMapper.readValue(resourceProfileResponse.getBody(),KakaoProfile.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException("user info mapping error");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("user info processing error");
        }
        return profile;
    }
}
