package TIAB.timebox.service.user;

import TIAB.timebox.dto.UserDtoReq;
import TIAB.timebox.dto.UserDtoRes;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDtoRes save(UserDtoReq dto) {
        User user=userRepository.findByKakaoId(dto.getKakaoId()).orElse(dtoToEntity(dto));
        return entityToDto(userRepository.save(user));
    }

    //Exception 처리하기
    @Override
    public UserDtoRes getUser(long id) throws Exception {
        User user=userRepository.findById(id).orElseThrow(()-> new Exception("User Not Found"));
        return entityToDto(user);
    }

    @Override
    public UserDtoRes findByKakaoId(long kakaoId) throws Exception {
        User user = userRepository.findByKakaoId(kakaoId).orElseThrow(()->new Exception("User Not Found"));
        return entityToDto(user);
    }

    @Override
    public List<UserDtoRes> getAllUsers() {
        return userRepository.findAll().stream().map(entity->entityToDto(entity)).collect(Collectors.toList());
    }

//    @Override
//    public User save(User user) {
//        User isUser=userRepository.findByKakaoId(user.getKakaoId()).orElse(null);
//        if(isUser!=null){
//            user.setId(isUser.getId());
//        }
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User getUser(long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public Optional<User> findByKakaoId(long kakaoId) {
//        return userRepository.findByKakaoId(kakaoId);
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
}
