package TIAB.timebox.service;
import TIAB.timebox.entity.User;
import TIAB.timebox.entity.Message;
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
        User user=userDao.findById(id).orElse(null);
        Message message=new Message();
        message.setUser(user);
        message.setContent(recvMessage.getContent());
        message.setDeadline(recvMessage.getDeadline());
        message.setWidth(recvMessage.getWidth());
        message.setHeight(recvMessage.getHeight());

        user.getMessages().add(message);
        return messageDao.save(message);
    }

    public List<Message> getAllMessages(long id){
        User user=userDao.findById(id).orElse(null);
        return messageDao.findAllByUser(user).orElse(null);
    }

    public Message getByMessageId(long id){
        return messageDao.findById(id).orElse(null);
    }
}
