package TIAB.timebox.repository;
import TIAB.timebox.entity.User;
import TIAB.timebox.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageDao extends JpaRepository<Message,Long> {
    public Optional<List<Message>> findAllByUser(User user);
    public Optional<Message> findById(long id);
    public Optional<List<Message>> findAllByDeadline(Date date);
}
