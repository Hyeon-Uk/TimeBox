package TIAB.timebox.repository;
import TIAB.timebox.entity.member.Member;
import TIAB.timebox.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    public List<Message> findAllByMember(Member member);
    public List<Message> findAllByDeadline(Date date);
}
