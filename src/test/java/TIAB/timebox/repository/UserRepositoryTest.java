package TIAB.timebox.repository;

import TIAB.timebox.entity.user.User;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    User user,user1,user2;

    @BeforeEach
    public void setUp(){
        user=User.builder()
                .email("test@naver.com")
                .imgSrc("testImg")
                .kakaoId(123l)
                .build();
        user1=User.builder()
                .email("test1@naver.com")
                .imgSrc("testImg1")
                .kakaoId(1231l)
                .build();
        user2=User.builder()
                .email("test2@naver.com")
                .imgSrc("testImg2")
                .kakaoId(1232l)
                .build();

    }

    @Test
    public void saveUser(){
        //given

        //when
        userRepository.save(user);

        //then
        assertThat(userRepository.findByEmail(user.getEmail()).isPresent()).isEqualTo(true);
    }

    @Test
    public void findUserById(){
        //given
        userRepository.save(user);

        //when
        User find=userRepository.findById(user.getId()).orElse(null);

        //then
        assertThat(find).isEqualTo(user);
    }

    @Test
    public void findByEmail(){
        //given
        userRepository.save(user);

        //when
        User find=userRepository.findByEmail(user.getEmail()).orElse(null);

        //then
        assertThat(find).isEqualTo(user);
    }

    @Test
    public void findByKakaoId(){
        //given
        userRepository.save(user);

        //when
        User find=userRepository.findByKakaoId(user.getKakaoId()).orElse(null);

        //then
        assertThat(find).isEqualTo(user);
    }

    @Test
    public void findAll(){
        int beforeSize=userRepository.findAll().size();

        //given && when && then
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        System.out.println(users.size());
        assertThat(users.size()).isEqualTo(beforeSize+1);
        assertThat(users).contains(user);

        userRepository.save(user1);
        List<User> users1 = userRepository.findAll();
        System.out.println(users.size());
        assertThat(users1.size()).isEqualTo(beforeSize+2);
        assertThat(users1).contains(user);
        assertThat(users1).contains(user1);

        userRepository.save(user2);
        List<User> users2 = userRepository.findAll();
        System.out.println(users.size());
        assertThat(users2.size()).isEqualTo(beforeSize+3);
        assertThat(users2).contains(user);
        assertThat(users2).contains(user1);
        assertThat(users2).contains(user2);
    }

    @Test
    public void updateEmail(){
        //given
        User saved = userRepository.save(user);

        //when
        saved.setEmail("changed@gmail.com");
        User changedEmailUser=userRepository.save(saved);

        //then
        assertThat(changedEmailUser).isEqualTo(saved);
    }
}
