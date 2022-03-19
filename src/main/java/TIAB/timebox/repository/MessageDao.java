package TIAB.timebox.repository;
import TIAB.timebox.domain.User;
import TIAB.timebox.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageDao extends JpaRepository<Message,Long> {
    public Optional<List<Message>> findAllByUser(User user);
    public Optional<Message> findById(long id);
}
