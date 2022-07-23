package TIAB.timebox.service.user;

import TIAB.timebox.dto.UserDtoReq;
import TIAB.timebox.dto.UserDtoRes;
import TIAB.timebox.entity.user.User;

import java.util.List;

public interface UserService {
    public UserDtoRes save(UserDtoReq dto);
    public UserDtoRes getUser(long id);
    public UserDtoRes findByKakaoId(long kakaoId);
    public List<UserDtoRes> getAllUsers();

    default User dtoToEntity(UserDtoReq dto){
        return User.builder()
                .email(dto.getEmail())
                .kakaoId(dto.getKakaoId())
                .imgSrc(dto.getImgSrc())
                .build();
    }

    default UserDtoRes entityToDto(User entity){
        return UserDtoRes.builder()
                .email(entity.getEmail())
                .kakaoId(entity.getKakaoId())
                .id(entity.getId())
                .user(entity)
                .build();
    }
}
