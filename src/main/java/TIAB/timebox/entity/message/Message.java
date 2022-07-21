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
@AllArgsConstructor
@NoArgsConstructor
@Table(name="message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="filename")
    private String filename;

    @Column(name="fileUrl")
    private String fileUrl;

    @Column(name="width")
    private int width;

    @Column(name="height")
    private int height;

    @Builder
    public Message(String filename,String fileUrl, int width, int height, Date deadline, User user) {
        this.filename = filename;
        this.fileUrl=fileUrl;
        this.width = width;
        this.height = height;
        this.deadline = deadline;
        this.user = user;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="deadline")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date deadline;

    @ManyToOne
    @JoinColumn(name="u_id")
    private User user;
}
