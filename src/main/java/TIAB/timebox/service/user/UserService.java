package TIAB.timebox.service.user;

import TIAB.timebox.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User save(User user);
    public User getUser(long id);
    public Optional<User> findByKakaoId(long kakaoId);
    public List<User> getAllUsers();
}
