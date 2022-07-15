package TIAB.timebox;

import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class UserDaoTest {

    @Autowired
    private UserRepository userRepository;

    private User user1,user2,user3;

    @BeforeEach
    public void setUp(){
        user1=new User();
        user1.setImgSrc("img1");
        user1.setKakaoId(1l);
        user1.setEmail("one@naver.com");

        user2=new User();
        user2.setImgSrc("img2");
        user2.setKakaoId(2l);
        user2.setEmail("two@naver.com");

        user3=new User();
        user3.setImgSrc("img3");
        user3.setKakaoId(3l);
        user3.setEmail("three@naver.com");
    }

    @Test
    public void saveUser(){
        int nowCnt=userRepository.findAll().size();

        User saved1=userRepository.save(user1);
        List<User> users1=userRepository.findAll();
        assertEquals(users1.size(),1+nowCnt);
        assertEquals(saved1.getKakaoId(),user1.getKakaoId());

        User saved2=userRepository.save(user2);
        List<User> users2=userRepository.findAll();
        assertEquals(users2.size(),2+nowCnt);
        assertEquals(saved2.getKakaoId(),user2.getKakaoId());

        User saved3=userRepository.save(user3);
        List<User> users3=userRepository.findAll();
        assertEquals(users3.size(),3+nowCnt);
        assertEquals(saved3.getKakaoId(),user3.getKakaoId());
    }

    @Test
    public void changeEmail(){
        User saved1=userRepository.save(user1);
        assertEquals(saved1.getEmail(),user1.getEmail());

        User findUser1=userRepository.findById(saved1.getId()).orElse(null);
        findUser1.setEmail("four@naver.com");
        User savedChange=userRepository.save(findUser1);

        assertEquals(findUser1,savedChange);
    }

    @Test
    public void getByEmail(){
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        User saved1=userRepository.findByEmail(user1.getEmail()).orElse(null);
        User saved2=userRepository.findByEmail(user2.getEmail()).orElse(null);
        User saved3=userRepository.findByEmail(user3.getEmail()).orElse(null);

        assertEquals(user1,saved1);
        assertEquals(user2,saved2);
        assertEquals(user3,saved3);

        User notFindUser=userRepository.findByEmail("noemail@naver.com").orElse(null);
        assertNull(notFindUser);
    }

}