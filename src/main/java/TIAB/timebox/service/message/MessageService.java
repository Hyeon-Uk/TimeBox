package TIAB.timebox.service.message;

import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.entity.message.Message;

import java.io.IOException;
import java.util.Date;

public interface MessageService {
    public MessageDtoRes save(long id, MessageDtoReq recvMessage) throws IOException;
    public MessageDtoRes getByMessageId(long id);

    default String generateFilename(MessageDtoReq dto){
        Date date=new Date();
        return Long.toString(date.getTime())+".png";
    }

    default Message dtoToEntity(MessageDtoReq messageDtoReq){
        return Message.builder()
                .filename(messageDtoReq.getFilename())
                .fileUrl(messageDtoReq.getFileUrl())
                .width(messageDtoReq.getWidth())
                .height(messageDtoReq.getHeight())
                .deadline(messageDtoReq.getDeadline())
                .user(messageDtoReq.getUser())
                .build();
    }

    default MessageDtoRes entityToDto(Message message){
        return MessageDtoRes.builder()
                .width(message.getWidth())
                .height(message.getHeight())
                .filename(message.getFilename())
                .fileUrl(message.getFileUrl())
                .deadline(message.getDeadline())
                .build();
    }
}
