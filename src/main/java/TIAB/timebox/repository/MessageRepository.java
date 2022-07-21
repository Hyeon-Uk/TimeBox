package TIAB.timebox.repository;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,Long> {
    public List<Message> findAllByUser(User user);
    public List<Message> findAllByDeadline(Date date);
}
