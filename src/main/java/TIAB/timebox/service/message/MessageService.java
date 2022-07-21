package TIAB.timebox.service.message;

import TIAB.timebox.dto.MessageDto;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    public Message save(long id, MessageDto recvMessage) throws IOException;
    public List<Message> getAllMessages(long id);
    public Message getByMessageId(long id);
}
