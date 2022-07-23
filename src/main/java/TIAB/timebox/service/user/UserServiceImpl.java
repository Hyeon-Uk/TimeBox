package TIAB.timebox.service.user;

import TIAB.timebox.dto.UserDtoReq;
import TIAB.timebox.dto.UserDtoRes;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public UserDtoRes getUser(long id) {
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException());
        return entityToDto(user);
    }

    @Override
    public UserDtoRes findByKakaoId(long kakaoId) {
        User user = userRepository.findByKakaoId(kakaoId).orElseThrow(()->new UserNotFoundException());
        return entityToDto(user);
    }

    @Override
    public List<UserDtoRes> getAllUsers() {
        return userRepository.findAll().stream().map(entity->entityToDto(entity)).collect(Collectors.toList());
    }
}
