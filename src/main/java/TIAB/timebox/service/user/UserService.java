package TIAB.timebox.service.user;

import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.UserRepository;
import TIAB.timebox.security.KakaoOAuth;
import TIAB.timebox.security.KakaoProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KakaoOAuth kakaoOAuth;

    public User socialLogin(String code){
        KakaoProfile profile=kakaoOAuth.getUserInfo(code);
        User user=new User();
        user.setKakaoId(profile.getId());
        user.setEmail(profile.getKakao_account().getEmail());
        user.setImgSrc(profile.getKakao_account().getProfile().getProfile_image_url());
        return user;
    }

    public User save(User user) {
        User isUser=userRepository.findByKakaoId(user.getKakaoId()).orElse(null);
        if(isUser!=null){
            user.setId(isUser.getId());
        }
        return userRepository.save(user);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
