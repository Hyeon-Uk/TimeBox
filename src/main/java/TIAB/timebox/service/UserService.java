package TIAB.timebox.service;

import TIAB.timebox.domain.User;
import TIAB.timebox.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    private UserDao userDao;

    public User save(User user) {
        User isUser=userDao.findByKakaoId(user.getKakaoId()).orElse(null);
        if(isUser!=null){
            user.setId(isUser.getId());
        }
        return userDao.save(user);
    }

    public User getUser(String email) {
        return userDao.findByEmail(email).orElse(null);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
