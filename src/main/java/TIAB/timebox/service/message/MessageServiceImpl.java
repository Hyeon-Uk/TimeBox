package TIAB.timebox.service.message;

import TIAB.timebox.dto.MessageDto;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    public Message save(long id, MessageDto recvMessage){
        User user=userRepository.findById(id).orElse(null);
        Message message=new Message();
        message.setUser(user);
        message.setContent(recvMessage.getContent());
        message.setDeadline(recvMessage.getDeadline());
        message.setWidth(recvMessage.getWidth());
        message.setHeight(recvMessage.getHeight());

        user.getMessages().add(message);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(long id){
        User user=userRepository.findById(id).orElse(null);
        return messageRepository.findAllByUser(user);
    }

    public Message getByMessageId(long id){
        return messageRepository.findById(id).orElse(null);
    }
}
