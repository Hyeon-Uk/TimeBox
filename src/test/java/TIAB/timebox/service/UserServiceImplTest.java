package TIAB.timebox.service;


import TIAB.timebox.dto.UserDtoReq;
import TIAB.timebox.dto.UserDtoRes;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.UserRepository;
import TIAB.timebox.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService; //

    @Mock
    private UserRepository userRepository;

    UserDtoReq userDtoReq1,userDtoReq2;
    UserDtoRes userDtoRes1,userDtoRes2;
    User beforeEntity1,afterEntity1,beforeEntity2,afterEntity2;
    @BeforeEach
    public void init(){
        userDtoReq1=UserDtoReq.builder()
                .kakaoId(1l)
                .imgSrc("imgsrc")
                .email("test123@gmail.com")
                .build();

        userDtoReq2=UserDtoReq.builder()
                .kakaoId(2l)
                .imgSrc("imgsrc1")
                .email("test1234@gmail.com")
                .build();

        beforeEntity1=userService.dtoToEntity(userDtoReq1);
        afterEntity1=userService.dtoToEntity(userDtoReq1);
        afterEntity1.setId(1l);

        beforeEntity2=userService.dtoToEntity(userDtoReq2);
        afterEntity2=userService.dtoToEntity(userDtoReq2);
        afterEntity2.setId(2l);

        userDtoRes1=userService.entityToDto(afterEntity1);
        userDtoRes2=userService.entityToDto(afterEntity2);

    }

    //객체 두개를 비교해주는 메소드
    public void assertThatDtoEqualToDto(UserDtoRes dto1,UserDtoRes dto2){
        assertThat(dto1.getKakaoId()).isEqualTo(dto2.getKakaoId());
        assertThat(dto1.getUser()).isEqualTo(dto2.getUser());
        assertThat(dto1.getId()).isEqualTo(dto2.getId());
        assertThat(dto1.getEmail()).isEqualTo(dto2.getEmail());
    }

    @Nested
    @DisplayName("Success Case")
    class Success{
        @Test
        public void mockUserRepository(){
            //given
            List<User> users=new ArrayList<>();
            users.add(afterEntity1);
            when(userRepository.findAll()).thenReturn(users);

            //when
            List<UserDtoRes> actual = userService.getAllUsers();

            //then
            assertThat(actual.size()).isEqualTo(1);
            assertThatDtoEqualToDto(actual.get(0),userDtoRes1);
        }

        @Test
        public void saveTest_새로운사용자(){
            //given
            when(userRepository.save(any(User.class))).thenReturn(afterEntity1);
            when(userRepository.findByKakaoId(anyLong())).thenReturn(Optional.ofNullable(afterEntity1));

            //when
            UserDtoRes actual=userService.save(userDtoReq1);

            //then
            assertThatDtoEqualToDto(actual,userDtoRes1);
        }

        @Test
        public void saveTest_기존사용자_이미지src변경(){
            //given
            String changedSrc="changedSrc";
            userDtoReq1.setImgSrc(changedSrc);
            afterEntity1.setImgSrc(changedSrc);
            userDtoRes1.setUser(afterEntity1);
            when(userRepository.save(any(User.class))).thenReturn(afterEntity1);

            //when
            UserDtoRes actual = userService.save(userDtoReq1);

            //then
            assertThatDtoEqualToDto(actual,userDtoRes1);
        }

        @Test
        public void findAllTest(){
            //given
            List<User> userList=new ArrayList<>();
            userList.add(afterEntity1);
            userList.add(afterEntity2);
            when(userRepository.findAll()).thenReturn(userList);
            List<UserDtoRes> expects=new ArrayList<>();
            expects.add(userDtoRes1);
            expects.add(userDtoRes2);

            //when
            List<UserDtoRes> actual = userService.getAllUsers();

            //then
            IntStream.range(0,actual.size()).forEach(i->{
                assertThatDtoEqualToDto(actual.get(i),expects.get(i));
            });
        }

        @Test
        public void findByKakaoIdTest(){
            //given
            when(userRepository.findByKakaoId(anyLong())).thenReturn(Optional.ofNullable(afterEntity1));

            //when
            UserDtoRes actual = userService.findByKakaoId(userDtoReq1.getKakaoId());

            //then
            assertThatDtoEqualToDto(actual,userDtoRes1);
        }

        @Test
        public void findByIdTest(){
            //given
            when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(afterEntity1));

            //when
            UserDtoRes actual = userService.getUser(1l);

            //then
            assertThatDtoEqualToDto(actual,userDtoRes1);
        }
    }

    @Nested
    @DisplayName("Failed Case")
    class Failed{
        @Test
        public void findByKakaoIdExceptionTest(){
            //given
            when(userRepository.findByKakaoId(anyLong())).thenReturn(Optional.ofNullable(null));

            //when & then
            assertThrows(UserNotFoundException.class,()->userService.findByKakaoId(3l));
        }

        @Test
        public void findByIdExceptionTest(){
            //given
            when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

            //when & then
            assertThrows(UserNotFoundException.class,()->userService.getUser(1l));
        }
    }
}
