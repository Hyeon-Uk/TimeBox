package TIAB.timebox.scheduler;

import TIAB.timebox.entity.Message;
import TIAB.timebox.repository.MessageDao;
import TIAB.timebox.service.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class Scheduler {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private Notification notification;

    @Scheduled(cron="0 0 0 * * *",zone="Asia/Seoul")
    public void sendCompleteMessage(){
        log.info("Send Message Start!");
        List<Message> messages=messageDao.findAllByDeadline(new Date()).orElse(null);
        notification.sendNotification(messages);
        log.info("Send Message Finish, send Count="+messages.size());
    }
}
