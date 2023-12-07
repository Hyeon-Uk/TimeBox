package TIAB.timebox.service.notification;

import TIAB.timebox.entity.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@Primary
@Slf4j
public class HtmlEmailNotification implements Notification {

    public final static String TEMPLATE="    <div style=\"display: flex;flex-direction: column;align-items: center;justify-content: center;background-color: rgba(243,228,207,0.1);\">\n" +
            "        <div style=\"font-size: 18px;\">\n" +
            "            <img src=\"https://github.com/Hyeon-Uk/TimeBox/blob/master/src/main/resources/static/images/box1_open_2.png?raw=true\">\n" +
            "        </div>\n" +
            "        <div style=\"font-size: 18px;\">맡겨두셨던 편지의 시간이 끝났습니다.</div>\n" +
            "        <a href=\"http://www.timebox.kro.kr/\" style=\"text-decoration: none;color: rgb(70,34,19);background-color: #f8efe1;width: 150px;height: 50px;text-align: center;display: flex;flex-direction: column;align-items: center;justify-content: center;border-radius: 15px;margin-top: 15px;\">찾으러가기</a>\n" +
            "    </div>";
    private static final String SUBJECT="[TimeBox] 메세지가 도착했습니다.";

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendNotification(List<Message> messages) {
        messages.forEach(message->{
            MimeMessage htmlEmail= javaMailSender.createMimeMessage();
            try {
                htmlEmail.setSubject(SUBJECT,"UTF-8");
                htmlEmail.setText(TEMPLATE,"UTF-8","html");
                htmlEmail.addRecipient(javax.mail.Message.RecipientType.TO,new InternetAddress(message.getMember().getEmail()));
                javaMailSender.send(htmlEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

    }
}
