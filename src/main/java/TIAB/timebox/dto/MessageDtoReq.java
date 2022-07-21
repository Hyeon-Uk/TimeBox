package TIAB.timebox.dto;

import TIAB.timebox.entity.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDtoReq {
    MultipartFile content;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date deadline;
    int width;
    int height;

    String filename;
    String fileUrl;
    User user;
    @Builder
    public MessageDtoReq(Date deadline, int width, int height, String filename, String fileUrl,User user) {
        this.deadline = deadline;
        this.width = width;
        this.height = height;
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.user=user;
    }
}
