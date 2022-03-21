package TIAB.timebox.scheduler;

import TIAB.timebox.entity.Message;
import TIAB.timebox.repository.MessageDao;
import TIAB.timebox.service.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private Notification notification;

    @Scheduled(cron="0 0 * * * *",zone="Asia/Seoul")
    public void sendCompleteMessage(){
        List<Message> messages=messageDao.findAllByDeadline(new Date()).orElse(null);
        notification.sendNotification(messages);
    }
}
