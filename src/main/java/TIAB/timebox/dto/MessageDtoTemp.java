package TIAB.timebox.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MessageDtoTemp {
    List<MultipartFile> content;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date deadline;
}
