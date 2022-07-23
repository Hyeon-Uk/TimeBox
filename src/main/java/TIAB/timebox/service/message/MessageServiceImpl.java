package TIAB.timebox.service.message;

import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public MessageDtoRes save(long id, MessageDtoReq messageDtoReq) throws IOException {
        User user=userRepository.findById(id).orElse(null);

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

    //없는 메세지 호출시 예외처리 하기
    public MessageDtoRes getByMessageId(long id) throws Exception {
        return entityToDto(messageRepository.findById(id).orElseThrow(()->new Exception("메세지가 없습니다.")));
    }
}
