package TIAB.timebox.dto;

import TIAB.timebox.entity.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDtoRes {
    long id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date deadline;
    int width;
    int height;
    String filename;
    String fileUrl;
    User user;
}
