package TIAB.timebox.service.message;

import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.exception.MessageNotFoundException;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(UserRepository userRepository,MessageRepository messageRepository){
        this.userRepository=userRepository;
        this.messageRepository=messageRepository;
    }

    @Override
    @Transactional
    public MessageDtoRes save(long id, MessageDtoReq messageDtoReq) throws IOException {
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException());

        MultipartFile file= messageDtoReq.getContent();
        String absolutePath=System.getProperty("user.dir")+"/src/main/resources/static/messagebox";
        File dir=new File(absolutePath);
        if(!dir.exists()) dir.mkdir();

        String filename=generateFilename(messageDtoReq);
        String fileUrl="/messagebox/"+filename;
        messageDtoReq.setFilename(filename);
        messageDtoReq.setFileUrl(fileUrl);
        messageDtoReq.setUser(user);

        File saveFile=new File(absolutePath,filename);
        file.transferTo(saveFile);
        Message message = dtoToEntity(messageDtoReq);

        user.getMessages().add(message);
        return entityToDto(messageRepository.save(message));
    }

    public MessageDtoRes getByMessageId(long id){
        return entityToDto(messageRepository.findById(id).orElseThrow(()->new MessageNotFoundException()));
    }
}
