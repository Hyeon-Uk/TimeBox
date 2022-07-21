package TIAB.timebox.repository;

import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MessageRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    User user1,user2,user3;
    Message yesterdayMessage,message1,message2,message3,message4,message5;

    @BeforeEach
    public void setUp(){
        user1=User.builder()
                .imgSrc("testimg1")
                .kakaoId(1l)
                .email("test1@gmail.com")
                .build();
        user2=User.builder()
                .imgSrc("testimg2")
                .kakaoId(2l)
                .email("test2@gmail.com")
                .build();
        user3=User.builder()
                .imgSrc("testimg3")
                .kakaoId(3l)
                .email("test3@gmail.com")
                .build();

        yesterdayMessage=Message.builder()
                .content("message yesterday")
                .width(1)
                .height(1)
                .deadline(new Date(new Date().getTime()-(1000*60*60*24*1)))
                .build();

        message1=Message.builder()
                .content("message1")
                .width(1)
                .height(1)
                .deadline(new Date())
                .build();

        message2=Message.builder()
                .content("message2")
                .width(2)
                .height(2)
                .deadline(new Date())
                .build();

        message3=Message.builder()
                .content("message3")
                .width(3)
                .height(3)
                .deadline(new Date())
                .build();

        message4=Message.builder()
                .content("message4")
                .width(4)
                .height(4)
                .deadline(new Date())
                .build();

        message5=Message.builder()
                .content("message5")
                .width(5)
                .height(5)
                .deadline(new Date())
                .build();
    }
    @Test
    public void repositoryIsNotNull(){
        assertThat(userRepository).isNotNull();
        assertThat(messageRepository).isNotNull();
    }
    @Test
    public void save(){
        //given
        User savedUser=userRepository.save(user1);
        message1.setUser(savedUser);
        savedUser.getMessages().add(message1);

        //when
        Message savedMessage=messageRepository.save(message1);
        User findUser=userRepository.findById(savedUser.getId()).orElse(null);

        //then
        assertThat(savedMessage).isEqualTo(message1);
        assertThat(findUser.getMessages()).contains(message1);
    }

    @Test
    public void findById(){
        //given
        Message saved = messageRepository.save(message1);

        //when
        Message findMessage = messageRepository.findById(saved.getId()).orElse(null);

        //then
        assertThat(findMessage).isEqualTo(saved);
    }

    @Test
    public void findAllByUser(){
        //given
        userRepository.save(user1);

        message1.setUser(user1);
        user1.getMessages().add(message1);
        message2.setUser(user1);
        user1.getMessages().add(message2);
        message3.setUser(user1);
        user1.getMessages().add(message3);

        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);


        //when
        List<Message> messages = messageRepository.findAllByUser(user1);
        //then
        assertThat(messages.size()).isEqualTo(3);
    }

    @Test
    public void findAllByDeadline(){
        //given
        userRepository.save(user1);
        message1.setUser(user1);
        message2.setUser(user1);
        yesterdayMessage.setUser(user1);

        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(yesterdayMessage);

        //when
        List<Message> todayMessages = messageRepository.findAllByDeadline(new Date());
        List<Message> yesterdayMessages = messageRepository.findAllByDeadline(new Date(new Date().getTime() - (1000 * 60 * 60 * 24 * 1)));
        Date yesterDay=new Date(new Date().getTime()-(1000*60*60*24*1));
        //then
        assertThat(todayMessages).contains(message1);
        assertThat(todayMessages).contains(message2);
        assertThat(yesterdayMessages).contains(yesterdayMessage);
    }
}