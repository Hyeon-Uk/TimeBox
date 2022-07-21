package TIAB.timebox.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MessageDtoRes {
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date deadline;
    int width;
    int height;
    String filename;
    String fileUrl;
    @Builder
    public MessageDtoRes(Date deadline, int width, int height, String filename, String fileUrl) {
        this.deadline = deadline;
        this.width = width;
        this.height = height;
        this.filename = filename;
        this.fileUrl = fileUrl;
    }
}
