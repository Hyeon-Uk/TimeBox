package TIAB.timebox.service;

import TIAB.timebox.entity.Message;

import java.util.Date;
import java.util.List;

public interface Notification {
    public void sendNotification(List<Message> message);
}
