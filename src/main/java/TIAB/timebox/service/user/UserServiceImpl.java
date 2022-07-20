package TIAB.timebox.service.user;

import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        User isUser=userRepository.findByKakaoId(user.getKakaoId()).orElse(null);
        if(isUser!=null){
            user.setId(isUser.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<User> findByKakaoId(long kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
