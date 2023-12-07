package TIAB.timebox.dto;

import TIAB.timebox.entity.member.Member;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDtoReq {
    MultipartFile content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date deadline;
    int width;
    int height;

    String filename;
    String fileUrl;
    Member member;
}
