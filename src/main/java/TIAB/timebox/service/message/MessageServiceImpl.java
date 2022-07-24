package TIAB.timebox.service.message;

import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.exception.MessageNotFoundException;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import TIAB.timebox.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private FileService fileService;

    @Autowired
    public MessageServiceImpl(UserRepository userRepository,MessageRepository messageRepository,FileService fileService){
        this.userRepository=userRepository;
        this.messageRepository=messageRepository;
        this.fileService=fileService;
    }

    @Override
    @Transactional
    public MessageDtoRes save(long id, MessageDtoReq messageDtoReq) throws IOException {
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException());

        fileService.save(messageDtoReq);

        messageDtoReq.setUser(user);
        Message message = dtoToEntity(messageDtoReq);

        user.getMessages().add(message);
        return entityToDto(messageRepository.save(message));
    }

    public MessageDtoRes getByMessageId(long id){
        return entityToDto(messageRepository.findById(id).orElseThrow(()->new MessageNotFoundException()));
    }
}
