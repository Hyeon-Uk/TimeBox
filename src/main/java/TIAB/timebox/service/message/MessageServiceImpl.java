package TIAB.timebox.service.message;

import TIAB.timebox.dto.MessageDto;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    private String generateFilename(MessageDto dto){
        Date date=new Date();
        return Long.toString(date.getTime())+".png";
    }

    @Override
    public Message save(long id, MessageDto messageDto) throws IOException {
        User user=userRepository.findById(id).orElse(null);

        MultipartFile file=messageDto.getContent();
        String absolutePath=resourceLoader.getResource("classpath:static/messagebox").getURL().getPath();
        String filename=generateFilename(messageDto);

        file.transferTo(new File(absolutePath+"/"+filename));
        Message message = Message.builder()
                            .user(user)
                            .filename(filename)
                            .fileUrl("/messagebox/"+filename)
                            .deadline(messageDto.getDeadline())
                            .width(messageDto.getWidth())
                            .height(messageDto.getHeight())
                            .build();

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
