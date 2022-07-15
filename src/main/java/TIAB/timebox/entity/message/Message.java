package TIAB.timebox.entity.message;

import TIAB.timebox.entity.BaseEntity;
import TIAB.timebox.entity.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="width")
    private int width;

    @Column(name="height")
    private int height;

    @Temporal(TemporalType.DATE)
    @Column(name="deadline")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date deadline;

    @ManyToOne
    @JoinColumn(name="u_id")
    private User user;
}
