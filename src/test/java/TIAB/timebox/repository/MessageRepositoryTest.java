package TIAB.timebox.repository;

import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    User user1,user2,user3;
    Message message1,message2,message3,message4,message5;

    @Before
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

        message1=new Message();
        message1.setHeight(1);
        message1.setWidth(1);
        message1.setDeadline(new Date(new Date().getTime()-(1000*60*60*24*1)));//어제 날짜
        message1.setContent("message1");

        message2=new Message();
        message2.setHeight(2);
        message2.setWidth(2);
        message2.setDeadline(new Date());
        message2.setContent("message2");

        message3=new Message();
        message3.setHeight(3);
        message3.setWidth(3);
        message3.setDeadline(new Date());
        message3.setContent("message3");

        message4=new Message();
        message4.setHeight(4);
        message4.setWidth(4);
        message4.setDeadline(new Date());
        message4.setContent("message4");

        message5=new Message();
        message5.setHeight(5);
        message5.setWidth(5);
        message5.setDeadline(new Date());
        message5.setContent("message5");
    }

    @Test
    public void makeMessage(){
        User savedUser=userRepository.save(user1);

        message1.setUser(savedUser);
        Message savedMessage= messageRepository.save(message1);
        savedUser.getMessages().add(message1);
        List<Message> saveMessage1= messageRepository.findAllByUser(savedUser).orElse(null);

        User saveUser1=userRepository.findById(savedUser.getId()).orElse(null);
        assertEquals(saveMessage1.size(),saveUser1.getMessages().size());
    }

    @Test
    public void getAllByDeadline(){
        User saved=userRepository.save(user1);
        message1.setUser(saved);//어제꺼
        message2.setUser(saved);
        message3.setUser(saved);
        message4.setUser(saved);
        message5.setUser(saved);

        messageRepository.save(message1);//어제꺼
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.save(message5);

        Date now=new Date();
        List<Message> messages= messageRepository.findAllByDeadline(now).orElse(null);
        assertEquals(messages.size(),4);
    }
}
