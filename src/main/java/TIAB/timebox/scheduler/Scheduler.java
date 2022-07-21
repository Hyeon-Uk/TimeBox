package TIAB.timebox.scheduler;

import TIAB.timebox.entity.message.Message;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.service.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class Scheduler {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private Notification notification;

    @Scheduled(cron="0 0 9 * * *",zone="Asia/Seoul")
    public void sendCompleteMessage(){
        log.info("Send Message Start!");
        List<Message> messages= messageRepository.findAllByDeadline(new Date());
        notification.sendNotification(messages);
        log.info("Send Message Finish, send Count="+messages.size());
    }
}
