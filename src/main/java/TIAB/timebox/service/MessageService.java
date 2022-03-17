package TIAB.timebox.service;
import TIAB.timebox.domain.User;
import TIAB.timebox.domain.Message;
import TIAB.timebox.dto.MessageDto;
import TIAB.timebox.repository.MessageDao;
import TIAB.timebox.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;

    public Message save(long id, MessageDto recvMessage){
        User user=userDao.getById(id);
        Message message=new Message();
        message.setUser(user);
        message.setContent(recvMessage.getContent());
        message.setDeadline(recvMessage.getDeadline());
        message.setWidth(recvMessage.getWidth());
        message.setHeight(recvMessage.getHeight());

        return messageDao.save(message);
    }

    public List<Message> getAllMessages(long id){
        User user=userDao.getById(id);
        return messageDao.findAllByUser(user).orElse(null);
    }
}
