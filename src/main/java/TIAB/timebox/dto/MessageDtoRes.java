package TIAB.timebox.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDtoRes {
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date deadline;
    int width;
    int height;
    String filename;
    String fileUrl;

}
