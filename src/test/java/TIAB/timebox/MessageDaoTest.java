package TIAB.timebox;

import TIAB.timebox.domain.Message;
import TIAB.timebox.repository.MessageDao;
import TIAB.timebox.repository.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import TIAB.timebox.domain.User;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MessageDaoTest {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

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
        User savedUser=userDao.save(user1);

        message1.setUser(savedUser);
        Message savedMessage=messageDao.save(message1);
        System.out.println("저장된 메세지의 유저 이메일 = "+savedMessage.getUser().getEmail());
        List<Message> saveMessage1=messageDao.findAllByUser(savedUser).orElse(null);

        System.out.println("사이즈 = "+saveMessage1.size());
        saveMessage1.forEach(e->{
            System.out.println(e.getId());
        });

        User saveUser1=userDao.getById(savedUser.getId());
        System.out.println("유저의 메세지 사이즈 = "+saveUser1.getMessages().size());
        saveUser1.getMessages().forEach(e->{
            System.out.println(e.getId());
        });
//        assertEquals(saveUser1.getMessages().size(),saveMessage1.size());

//        message2.setUser(savedUser);
//        messageDao.save(message2);
//        List<Message> saveMessage2=messageDao.findAllByUser(savedUser).orElse(null);
//        User saveUser2=userDao.getById(savedUser.getId());
//        assertEquals(saveUser2.getMessages().size(),saveMessage2.size());
//

    }
}
