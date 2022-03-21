package TIAB.timebox.service;

import TIAB.timebox.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailNotification implements Notification{

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SUBJECT="[TimeBox] 메세지가 도착했습니다.";
    private static final String TEXT="<h1>과거에 맡겨두셨던 편지의 오픈시간이 되었습니다.";

    @Override
    public void sendNotification(List<Message> messages) {
        if(messages==null) return;
        messages.forEach(message->{
            SimpleMailMessage msg=new SimpleMailMessage();
            msg.setTo(message.getUser().getEmail());
            msg.setSubject(SUBJECT);
            msg.setText(TEXT);
            javaMailSender.send(msg);
        });
    }
}
