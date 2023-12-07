package TIAB.timebox.service.notification;

import TIAB.timebox.entity.message.Message;

import java.util.List;

public interface Notification {
    void sendNotification(List<Message> messages);
}
